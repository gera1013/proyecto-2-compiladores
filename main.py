from socketserver import StreamRequestHandler
from structures import *
from constants import *

from antlr4 import *
from dist.YAPLGrammerLexer import YAPLGrammerLexer
from dist.YAPLGrammerParser import YAPLGrammerParser
from dist.YAPLGrammerVisitor import YAPLGrammerVisitor

scopes = []

CHECKPOINT = 'NOW'

class YAPLVisitor(YAPLGrammerVisitor):
    def visitProgram(self, ctx):
        self.recon = True

        self.symbol_table = SymbolTableStack()
        self.type_table = TypeTable()
        self.error_list = []

        self.classes_list = StructureList(YAPLStructures.CLASS)
        self.methods_list = StructureList(YAPLStructures.METHOD)
        self.attributes_list = StructureList(YAPLStructures.ATTRIBUTE)
        self.arguments_list = StructureList(YAPLStructures.ARGUMENT)

        self.classes_list.init_object(self.methods_list)
        self.classes_list.init_string(self.methods_list)

        self.symbol_table.enter_scope()
        self.symbol_table.add_symbol("abort", YAPLTypes.OBJECT)
        self.symbol_table.add_symbol("type_name", YAPLTypes.STRING)
        self.symbol_table.add_symbol("copy", YAPLTypes.SELF_TYPE)
        self.symbol_table.add_symbol("length", YAPLTypes.INT)
        self.symbol_table.add_symbol("concat", YAPLTypes.STRING)
        self.symbol_table.add_symbol("sub_str", YAPLTypes.STRING)

        for c in ctx.classP():
            print(c.className.text)
            self.visit(c)

        print(self.classes_list.table)
        print(self.methods_list.table)
        print(self.attributes_list.table)
        print(self.arguments_list.table)

        print("RECON FASE ENDED \n\n\n\n")
        self.recon = False

        for c in ctx.classP():
            status = self.visit(c)
            print("END CLASS\nSTATUS: {}\n\n".format(status))

            if status == Status.ERROR:
                print("FAILLLLLL")
                return Status.ERROR
        
        scopes.append(self.symbol_table.exit_scope())

        return Status.OKAY


    def visitClassP(self, ctx):
        if self.recon:
            self.type_table.add_type(ctx.className.text, ctx.parentClass.text if ctx.inherits else None)
            for i in ctx.feature():
                result = self.visit(i)
                
                methods = result[0]
                attributes = result[1]

                self.classes_list.add_structure([ctx.className.text, attributes, methods])

            return Status.OKAY

        self.symbol_table.add_symbol(ctx.className.text, ctx.className.text)
        self.symbol_table.enter_scope()

        if not ctx.CLASSKEY:
            self.error_list.append(str(ctx.getRuleIndex()) + " No hay declaración de clase.")
            return Status.ERROR

        if ctx.inherits:
            if ctx.parentClass.text == YAPLTypes.IO:
                self.classes_list.init_io(self.methods_list)
                self.symbol_table.add_symbol("out_string", YAPLTypes.SELF_TYPE)
                self.symbol_table.add_symbol("out_int", YAPLTypes.SELF_TYPE)
                self.symbol_table.add_symbol("in_string", YAPLTypes.STRING)
                self.symbol_table.add_symbol("in_int", YAPLTypes.INT)

            if ctx.parentClass.text not in self.type_table.types.keys():
                self.error_list.append(str(ctx.getRuleIndex()) + " Clase padre '{}' no definida en el lenguaje".format(ctx.parentClass.text))
                return Status.ERROR

        for i in ctx.feature():
            result = self.visit(i)
            print("TABLE", self.symbol_table.get_scope().table)
            if result[0] == Status.ERROR:
                return Status.ERROR

            methods = result[1]
            attributes = result[2]


        scopes.append(self.symbol_table.exit_scope())

        return Status.OKAY

    def visitFeature(self, ctx):
        if self.recon:
            mets = []
            atts = []

            if ctx.fmethod != None:
                result = self.visit(ctx.fmethod)
                if result[0] == Status.OKAY:
                    mets.append(result[1])
                else:
                    return [Status.ERROR]

            if ctx.fattribute != None:
                result = self.visit(ctx.fattribute)
                atts.append(result)

            return mets, atts

        methods = []
        attributes = []

        if ctx.fmethod != None:
            result = self.visit(ctx.fmethod)
            if result[0] == Status.OKAY:
                methods.append(result[1])
            else:
                return [Status.ERROR]

        if ctx.fattribute != None:
            result = self.visit(ctx.fattribute)
            if result[0] == Status.OKAY:
                attributes.append(result[1])
            else:
                return [Status.ERROR]

        return Status.OKAY, methods, attributes


    def visitMethod(self, ctx):
        name = ctx.name.text
        type = ctx.returnType.text
        
        if self.recon:
            args = self.visit(ctx.argumentList)
            self.methods_list.add_structure([name, type, args[1]])

            print("ADDED METHOD")

            return Status.OKAY, self.methods_list.table[name]

        if type not in self.type_table.types.keys():
            self.error_list.append(str(ctx.getRuleIndex()) + " Error al declarar metodo '{}', tipo '{}' declarado no existe".format(name, type))
            return Status.ERROR

        self.symbol_table.add_symbol(name, type)

        self.symbol_table.enter_scope()

        self.visit(ctx.argumentList)

        expr = self.visit(ctx.mainExpr)

        print("MAIN EXPR TYPE", expr)

        scopes.append(self.symbol_table.exit_scope())

        if expr == Status.ERROR:
            return Status.ERROR

        if expr == type or expr == None or expr == YAPLTypes.SELF_TYPE or self.type_table.types[expr]["inherits"] == type:
            return Status.OKAY, self.methods_list.table[name]
        else:
            self.error_list.append(str(ctx.getRuleIndex()) + " El valor de retorno del metodo '{}' debe ser de tipo '{}'".format(name, type))
            return Status.ERROR


    def visitAttribute(self, ctx):
        name = ctx.name.text
        type = ctx.typeName.text

        if self.recon:
            self.attributes_list.add_structure([name, type])
            return {"name": name, "type": type}

        if type not in self.type_table.types.keys():
            self.error_list.append(str(ctx.getRuleIndex()) + " Error al declarar variable '{}', el tipo '{}' no es reconocido por el sistema".format(name, type))
            return Status.ERROR

        if ctx.getChildCount() > 3:
            expression_type = self.visit(ctx.getChild(4))

            print("EXPRESSION TYPE", expression_type)

            if expression_type != type:
                self.error_list.append(str(ctx.getRuleIndex()) + " Error al declarar variable '{}', tipo '{}' no concuerda con tipo '{}' establecido".format(name, expression_type, type))
                return Status.ERROR
        
        self.symbol_table.add_symbol(name, type)
        # self.attributes_list.add_structure([name, type])

        return Status.OKAY, {"name": name, "type": type}


    def visitArguments(self, ctx):
        args = []
        error = False
        if self.recon:
            for f in ctx.formal():
                print({"name": f.name.text, "type": f.typeName.text})
                self.arguments_list.add_structure([f.name.text, f.typeName.text])
                args.append({"name": f.name.text, "type": f.typeName.text})
        else:
            for f in ctx.formal():
                payload = self.visit(f)
                if payload[0] == Status.ERROR:
                    error = True

                args.append(payload[1])

        return (Status.OKAY, args) if not error else Status.ERROR

    def visitFormal(self, ctx):
        name = ctx.name.text
        type = ctx.typeName.text

        if type not in self.type_table.types.keys():
            self.error_list.append(str(ctx.getRuleIndex()) + " Error al declarar variable '{}', el tipo '{}' no es reconocido por el sistema".format(name, type))
            return Status.ERROR

        self.arguments_list.add_structure([name, type])
        self.symbol_table.add_symbol(name, type)

        return Status.OKAY, {"name": name, "type": type}

    def visitExpression(self, ctx):
        result = None
        next_expr_res = None

        print("START NEXT EXPRESSION EVALUATION")
        next_expr_res = self.visit(ctx.ops())

        if ctx.calls != None:
            result = self.visit(ctx.calls)
            print("OVERWRITE RETURN", result)
        
        if ctx.ifexpression != None:
            result = self.visit(ctx.ifexpression)
        
        if ctx.whileexpression != None:
            result = self.visit(ctx.whileexpression)

            return YAPLTypes.OBJECT if result != Status.ERROR else result
        
        if ctx.letexpression != None:
            result = self.visit(ctx.letexpression)
        
        if ctx.newDeclaration != None:
            result = self.visit(ctx.newDeclaration)
        
        if ctx.voidexpression != None:
            return self.visit(ctx.voidexpression)
        
        if ctx.notexpression != None:
            return self.visit(ctx.notexpression)
        
        if ctx.parenthesisexpression != None:
            result = self.visit(ctx.parenthesisexpression)
        
        if ctx.innerexpression != None:
            return self.visit(ctx.innerexpression)
        
        if ctx.negationexpression != None:
            return self.visit(ctx.negationexpression)

        if ctx.trueexpression != None:
            return YAPLTypes.BOOL
        
        if ctx.falseexpression != None:
            return YAPLTypes.BOOL
        
        if ctx.stringexpression != None:
            return YAPLTypes.STRING
        
        if ctx.integerexpression != None:
            return YAPLTypes.INT

        if ctx.selfexpression != None:
            return YAPLTypes.SELF_TYPE

        return next_expr_res if next_expr_res != None else result

    def visitIfx(self, ctx):
        print("STARTING IF EVALUATION")
        if_exp_result = self.visit(ctx.ifexp)
        print("STARTING THEN EVALUATION")
        then_exp_result = self.visit(ctx.thenexp)
        print("STARTING ELSE EVALUATION")
        else_exp_result = self.visit(ctx.elseexp)

        print("IF RESULT HERE <-------")
        print(if_exp_result, then_exp_result, else_exp_result)

        if if_exp_result != YAPLTypes.BOOL:
            self.error_list.append(str(ctx.getRuleIndex()) + " Condicion de if no es de tipo 'Bool'")
            return Status.ERROR

        return else_exp_result

    def visitWhilex(self, ctx):
        sentence_result = self.visit(ctx.whileSentence)
        action_result = self.visit(ctx.whileAction) 
        
        if sentence_result != YAPLTypes.BOOL:
            self.error_list.append(str(ctx.getRuleIndex()) + " Condicion de while no es de tipo 'Bool'")
            return Status.ERROR

        return action_result

    def visitLetx(self, ctx):
        self.symbol_table.enter_scope()
        for exp in ctx.attribute():
            self.visit(exp)

        print("STARTING LET EVALUATION")
        result = self.visit(ctx.inexp)

        print("LET RESULT", result)

        scopes.append(self.symbol_table.exit_scope())
        return result

    def visitVoidx(self, ctx):
        result = self.visit(ctx.expression())

        return YAPLTypes.BOOL if result != Status.ERROR else result

    def visitNotx(self, ctx):
        result = self.visit(ctx.expression())

        return YAPLTypes.BOOL if result != Status.ERROR else result

    def visitParenthesisx(self, ctx):
        print("STARTING PARENTHESIS EXPRESSION EVALUATION")
        return self.visit(ctx.expression())

    def visitNegationx(self, ctx):
        print("STARTING NEGATION EXPRESSION EVALUATION")
        self.visit(ctx.expression())

        print("END NEGATION EXPRESSION EVALUATION")
        return YAPLTypes.INT

    def visitDeclaration(self, ctx):
        print("START NEW DECLARATION")
        type = ctx.getChild(1).getText()
        print(type)

        if type not in self.type_table.types.keys():
            self.error_list.append(str(ctx.getRuleIndex()) + " Error al crear instancia de tipo '{}', no reconocido por el sistema".format(type))
            return Status.ERROR
            
        return type

    def visitMultipleExpr(self, ctx):
        types = []
        for expression in ctx.expression():
            types.append(self.visit(expression))

        print("MULTI TYPES", types)

        return types[-1]

    def visitNextOps(self, ctx):
        print("START OPS EVALUATION")
        if ctx.mCall != None:
            name, args = self.visit(ctx.mCall)

            method = self.methods_list.get_structure(name)

            if len(method.args) == len(args):
                for i in range(len(method.args)):
                    if args[i][0] == Status.ERROR:
                        return Status.ERROR

                    if args[i].upper() == YAPLTypes.SELF_TYPE:
                        args[i] = list(self.symbol_table.bottom().table.keys())[-1]

                    if method.args[i].get('type') != args[i] and method.args[i].get('type') != self.type_table.types.get(args[i]).get('inherits'):
                        print("TRIED TYPES: ", method.args[i].get('type') , args[i])
                        self.error_list.append(str(ctx.getRuleIndex()) + " Los tipos de los argumentos para el metodo '{}' no concuerdan".format(name))
                        return Status.ERROR

                return method.type
            else:
                if len(method.args) > len(args):
                    self.error_list.append(str(ctx.getRuleIndex()) + " El metodo {} recibe {} parametros, se recibieron {}".format(name, len(method.args), len(args)))
                    return Status.ERROR
                
                if len(method.args) < len(args):
                    self.error_list.append(str(ctx.getRuleIndex()) + " El metodo {} recibe {} parametros, se recibieron {}".format(name, len(method.args), len(args)))
                    return Status.ERROR
            


        operator = ctx.getChild(0).getText()
        print(operator, "WILL RETURN")

        if ctx.secondexpression != None:
            expression_type = self.visit(ctx.secondexpression)
            print("SECOND EXPRESSION TYPE ", expression_type)

        if expression_type != YAPLTypes.INT:
            self.error_list.append(str(ctx.getRuleIndex()) + " Los tipos no concuerdan, se intento un tipo '{}' en operacion de 'Int'".format(expression_type))
            return Status.ERROR

        return YAPLTypes.INT if operator in ['*', '+', '-', '/'] else YAPLTypes.BOOL

    def visitEscexpression(self, ctx):
        print("ENDED IN ESCAPE EXPRESSION")
        return None

    def visitMethodCall(self, ctx):
        print("START METHOD CALL EVALUATION")
        name = ctx.methodName.text

        args = []
        for e in ctx.expression():
            e_type = self.visit(e)
            args.append(e_type)

        return name, args

    def visitOverwrite(self, ctx):
        print("START OVERWRITE EVALUATION")
        if ctx.attr != None:
            actual_type = self.symbol_table.lookup(ctx.name.text)
            if actual_type[0] == Status.ERROR:
                self.error_list.append(str(ctx.getRuleIndex()) + " La variable '{}' no fue declarada anteriormente".format(ctx.name.text))
                return Status.ERROR
            
            declared_type = self.visit(ctx.attr)
            if actual_type[1].get('type') != declared_type:
                self.error_list.append(str(ctx.getRuleIndex()) + " La asignacion a variable {} no concuerda con tipo {}, se intentó {}".format(ctx.name.text, actual_type[1].get('type'), declared_type))
                return Status.ERROR
            
            return actual_type[1].get('type')
        
        elif ctx.fun != None:
            payload = self.symbol_table.lookup(ctx.name.text)
            print("LOOKUP PAYLOAD", payload)
            if payload[0] == Status.ERROR:
                self.error_list.append(str(ctx.getRuleIndex()) + " El metodo '{}' no fue declarado anteriormente".format(ctx.name.text))
                return Status.ERROR

            fun_name = payload[1].get('name')
            method = self.methods_list.get_structure(fun_name)
            args_list = self.visit(ctx.fun)

            print(fun_name)

            if len(method.args) == len(args_list):
                print(method.args, args_list)
                for i in range(len(method.args)):
                    print("ARGS", args_list)
                    print("METHOD ARGS", method.args)
                    if args_list[i] == Status.ERROR:
                        return Status.ERROR

                    if method.args[i].get('type') != args_list[i] and method.args[i].get('type') != self.type_table.types.get(args_list[i]).get('inherits'):
                        print("TRIED TYPES: ", method.args[i].get('type') , args_list[i])
                        self.error_list.append(str(ctx.getRuleIndex()) + " Los tipos de los argumentos para el metodo '{}' no concuerdan".format(fun_name))
                        return Status.ERROR
                    
                print(method.type)
                return method.type
            else:
                if len(method.args) > len(args_list):
                    self.error_list.append(str(ctx.getRuleIndex()) + " El metodo {} recibe {} parametros, se recibieron {}".format(fun_name, len(method.args), len(args_list)))
                    return Status.ERROR
                
                if len(method.args) < len(args_list):
                    self.error_list.append(str(ctx.getRuleIndex()) + " El metodo {} recibe {} parametros, se recibieron {}".format(fun_name, len(method.args), len(args_list)))
                    return Status.ERROR
        else:
            payload = self.symbol_table.lookup(ctx.name.text)
            print("NAME", ctx.name.text)
            print("PAYLOAD", payload)
            if payload[0] == Status.ERROR:
                self.error_list.append(str(ctx.getRuleIndex()) + " La variable '{}' no ha sido declarada".format(ctx.name.text))
                return Status.ERROR

            print(payload[1].get('type'))    
            return payload[1].get('type')

    def visitAttrWrite(self, ctx):
        return self.visit(ctx.exp)

    def visitFunCall(self, ctx):
        print("START FUNCTION CALL EVALUATION")
        types = []
        if ctx.argOne != None:
            types.append(self.visit(ctx.argOne))
        if ctx.argsMore != None:
            for arg in ctx.more():
                types.append(self.visit(arg))

        return types

    
    def visitMore(self, ctx):
        return self.visit(ctx.exp)




def do_visit(d):
    data = InputStream(d)
    # lexer
    lexer = YAPLGrammerLexer(data)
    stream = CommonTokenStream(lexer)
    # parser
    parser = YAPLGrammerParser(stream)
    tree = parser.program()
    # evaluator
    visitor = YAPLVisitor()
    output = visitor.visit(tree)
    
    for scope in scopes:
        print(scope.table)

    print(visitor.classes_list.table)
    print(visitor.methods_list.table)
    print(visitor.attributes_list.table)
    print(visitor.arguments_list.table)

    return output, visitor.error_list
