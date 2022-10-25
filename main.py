from ctypes import pointer
from os import stat
from socketserver import StreamRequestHandler
from structures import *
from constants import *

from antlr4 import *
from dist.YAPLGrammerLexer import YAPLGrammerLexer
from dist.YAPLGrammerParser import YAPLGrammerParser
from dist.YAPLGrammerVisitor import YAPLGrammerVisitor

scopes = []

class YAPLVisitor(YAPLGrammerVisitor):
    def visitProgram(self, ctx):
        self.recon = True
        self.lets = 0

        self.symbol_table = SymbolTableStack()
        self.type_table = TypeTable()
        self.error_list = []
        self.quadruples = []

        self.classes_list = StructureList(YAPLStructures.CLASS)
        self.methods_list = StructureList(YAPLStructures.METHOD)
        self.attributes_list = StructureList(YAPLStructures.ATTRIBUTE)
        self.arguments_list = StructureList(YAPLStructures.ARGUMENT)

        self.classes_list.init_object(self.methods_list)
        self.classes_list.init_string(self.methods_list)

        self.symbol_table.enter_scope()
        self.symbol_table.add_symbol("abort", YAPLTypes.OBJECT, None)
        self.symbol_table.add_symbol("type_name", YAPLTypes.STRING, None)
        self.symbol_table.add_symbol("copy", YAPLTypes.SELF_TYPE, None)
        self.symbol_table.add_symbol("length", YAPLTypes.INT, None)
        self.symbol_table.add_symbol("concat", YAPLTypes.STRING, None)
        self.symbol_table.add_symbol("sub_str", YAPLTypes.STRING, None)

        for c in ctx.classP():
            print(c.className.text)
            self.visit(c)

        print("RECON FASE ENDED \n\n")
        self.recon = False

        for c in ctx.classP():
            status = self.visit(c)
            print("END CLASS\nSTATUS: {}\n\n".format(status))

            if status == Status.ERROR:
                print("FAIL")
                return Status.ERROR
        
        scopes.append(self.symbol_table.exit_scope())

        return Status.OKAY


    def visitClassP(self, ctx):
        size = 0
        if self.recon:
            for i in ctx.feature():
                result = self.visit(i)
                
                methods = result[0]
                attributes = result[1]
                
                for attribute in attributes:
                    print(attribute)
                    size += attribute['size']

                self.classes_list.add_structure([ctx.className.text, attributes, methods])

            print("ADDED TYPE {} OF SIZE {}".format(ctx.className.text, size))
            self.type_table.add_type(ctx.className.text, size, ctx.parentClass.text if ctx.inherits else None)
            return Status.OKAY

        self.symbol_table.add_symbol(ctx.className.text, ctx.className.text, size)
        self.symbol_table.enter_scope(ctx.className.text)

        if not ctx.CLASSKEY:
            self.error_list.append("LINE {}: No hay declaración de clase.".format(ctx.start.line))
            return Status.ERROR

        if ctx.inherits:
            if ctx.parentClass.text == YAPLTypes.IO:
                self.classes_list.init_io(self.methods_list)
                self.symbol_table.add_symbol("out_string", YAPLTypes.SELF_TYPE, None)
                self.symbol_table.add_symbol("out_int", YAPLTypes.SELF_TYPE, None)
                self.symbol_table.add_symbol("in_string", YAPLTypes.STRING, None)
                self.symbol_table.add_symbol("in_int", YAPLTypes.INT, None)

            if ctx.parentClass.text not in self.type_table.types.keys():
                self.error_list.append("LINE {}: Clase padre '{}' no definida en el lenguaje".format(ctx.start.line, ctx.parentClass.text))
                return Status.ERROR

        code = ""

        for i in ctx.feature():
            result = self.visit(i)
            print("TABLE", self.symbol_table.get_scope().table)
            if result[0] == Status.ERROR:
                return Status.ERROR

            methods = result[1]
            attributes = result[2]
            feature_code = result[3]

            code += "\n" + feature_code

        print("CODE FOR CLASS: {}".format(code))

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
                methods.append(result.object)
            else:
                return [Status.ERROR]

            code = result.code

        if ctx.fattribute != None:
            result = self.visit(ctx.fattribute)
            if result[0] == Status.OKAY:
                attributes.append(result[1])

                code = result[1].get('code')
            else:
                return [Status.ERROR]

        return Status.OKAY, methods, attributes, code


    def visitMethod(self, ctx):
        name = ctx.name.text
        type = ctx.returnType.text
        
        if self.recon:
            self.symbol_table.enter_scope(name)
            args = self.visit(ctx.argumentList)
            self.methods_list.add_structure([name, type, args[1]])

            print("ADDED METHOD")
            self.symbol_table.exit_scope()
            return Status.OKAY, self.methods_list.table[name]

        if type not in self.type_table.types.keys():
            self.error_list.append("LINE {}: Error al declarar metodo '{}', tipo '{}' declarado no existe".format(ctx.start.line, name, type))
            return Status.ERROR
        
        self.symbol_table.add_symbol(name, type, self.type_table.get_type_size(type))

        self.symbol_table.enter_scope(name)

        arguments_result = self.visit(ctx.argumentList)

        expr = self.visit(ctx.mainExpr)

        print("MAIN EXPR TYPE", expr)

        scopes.append(self.symbol_table.exit_scope())

        if expr.status == Status.ERROR:
            return Status.ERROR

        if expr.type == type or expr.type == None or expr.type == YAPLTypes.SELF_TYPE or self.type_table.types[expr.type]["inherits"] == type:
            print("METHOD RETURNING VALUES", Status.OKAY, self.methods_list.table[name], arguments_result[2])
            return PAYLOAD(status=Status.OKAY, type=expr.type, code=expr.code, size=arguments_result[2], pointer=expr.pointer, object=self.methods_list.table[name])
        else:
            self.error_list.append("LINE {}: El valor de retorno del metodo '{}' debe ser de tipo '{}'".format(ctx.start.line, name, type))
            return Status.ERROR


    def visitAttribute(self, ctx):
        name = ctx.name.text
        type = ctx.typeName.text
        code = ""

        current_scope = self.symbol_table.get_scope()
        scope = current_scope.title
        pointer = current_scope.get_pointer()

        if self.recon:
            self.attributes_list.add_structure([name, type])
            return {"name": name, "type": type, "size": self.type_table.get_type_size(type)}

        if type not in self.type_table.types.keys():
            self.error_list.append("LINE {}: Error al declarar variable '{}', el tipo '{}' no es reconocido por el sistema".format(ctx.start.line, name, type))
            return Status.ERROR

        if ctx.getChildCount() > 3:
            expression_result = self.visit(ctx.getChild(4))
            
            expression_type = expression_result.type

            print("EXPRESSION TYPE", expression_type)

            if expression_type != type:
                self.error_list.append("LINE {}: Error al declarar variable '{}', tipo '{}' no concuerda con tipo '{}' establecido".format(ctx.start.line, name, expression_type, type))
                return Status.ERROR

            if expression_result.pointer == None:
                code = "{}[{}] <{}> = {}".format(scope, pointer, name, expression_result.code)
            else:
                code = "{}\n{}[{}] <{}> = {}".format(expression_result.code, scope, pointer, name, expression_result.pointer)
        else:
            code = "{}[{}] <{}> = getAddress()".format(scope, pointer, name)

        self.symbol_table.add_symbol(name, type, self.type_table.get_type_size(type))

        print("\n\n\n CODE: {}\n\n\n".format(code))

        return Status.OKAY, {"name": name, "type": type, "size": self.type_table.get_type_size(type), "code": code, "pointer": pointer}


    def visitArguments(self, ctx):
        args = []
        size = 0
        error = False
        
        if self.recon:
            s = 0
            for f in ctx.formal():
                print({"name": f.name.text, "type": f.typeName.text, "scope": self.symbol_table.get_scope().title})
                self.arguments_list.add_structure([f.name.text, f.typeName.text, self.symbol_table.get_scope().title, s])
                args.append({"name": f.name.text, "type": f.typeName.text, "scope": self.symbol_table.get_scope().title, "address": s})
                
                s += self.type_table.get_type_size(f.typeName.text)
                print("SIZE", str(s))
        else:
            for f in ctx.formal():
                payload = self.visit(f)
                if payload[0] == Status.ERROR:
                    error = True

                size += self.type_table.get_type_size(f.typeName.text)
                args.append(payload[1])

        return (Status.OKAY, args, size) if not error else Status.ERROR

    def visitFormal(self, ctx):
        name = ctx.name.text
        type = ctx.typeName.text
        size = self.type_table.get_type_size(type)

        if type not in self.type_table.types.keys():
            self.error_list.append("LINE {}: Error al declarar variable '{}', el tipo '{}' no es reconocido por el sistema".format(ctx.start.line, name, type))
            return Status.ERROR

        self.arguments_list.add_structure([name, type, self.symbol_table.get_scope().title, size])
        self.symbol_table.add_symbol(name, type, size)

        current_scope = self.symbol_table.get_scope()
        pointer = current_scope.get_pointer()

        return Status.OKAY, {"name": name, "type": type, "size": size, "code": "", "pointer": pointer}

    def visitExpression(self, ctx):
        result = None
        next_expr_res = None

        print("START NEXT EXPRESSION EVALUATION")
        next_expr_res = self.visit(ctx.ops())
        not_escape = next_expr_res.type != None

        if ctx.calls != None:
            result = self.visit(ctx.calls)
            print("RESULT OF VISITING CALLS", result)
            code = ""

            if not_escape:
                if ctx.ops().mCall != None:
                    return next_expr_res

                print("THIS OPS RESULTED IN AN OPERATION")
                var = self.symbol_table.lookup(result.name)[1]
                print(var)
                print(result)
                print(next_expr_res)
                if next_expr_res.type == YAPLTypes.BOOL:
                    code = "{}{}{}[{}] <{}> {}".format(result.code + "\n" if result.code is not None else "", next_expr_res.code + "\n" if next_expr_res.code != '' else "", var.get('scope'), var.get('address'), result.name, next_expr_res.pointer)

                    print("THIS OPS CODE", code)

                    return PAYLOAD(status=Status.OKAY, type=YAPLTypes.BOOL, code=code)
                else:
                    label = self.symbol_table.get_scope().add_temp()
                    if result.pointer == None:
                        code = "{}{}{} = {}[{}] <{}> {}".format(result.code + "\n" if result.code is not None else "", next_expr_res.code + "\n" if next_expr_res.code != '' else "", label, var.get('scope'), var.get('address'), result.name, next_expr_res.pointer)
                    else:
                        code = "{}{}{} = {} {}".format(result.code + "\n" if result.code is not None else "", next_expr_res.code + "\n" if next_expr_res.code != '' else "", label, result.pointer, next_expr_res.pointer)

                    print("THIS OPS CODE", code)
                    return PAYLOAD(status=Status.OKAY, type=YAPLTypes.INT, code=code, pointer=label)
            
            print("THIS OPS RESULTED IN A METHOD CALL")
            return result
        
        if ctx.ifexpression != None:
            result = self.visit(ctx.ifexpression)
        
        if ctx.whileexpression != None:
            result = self.visit(ctx.whileexpression)

            return PAYLOAD(status=Status.OKAY, type=YAPLTypes.OBJECT) if result.status != Status.ERROR else result
        
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
            code = "true"

            return PAYLOAD(status=Status.OKAY, type=YAPLTypes.BOOL, code="", pointer=code)
        
        if ctx.falseexpression != None:
            code = "false"

            return PAYLOAD(status=Status.OKAY, type=YAPLTypes.BOOL, code="", pointer=code)
        
        if ctx.stringexpression != None:
            code = ctx.stringexpression.text
            
            return PAYLOAD(status=Status.OKAY, type=YAPLTypes.STRING, code="", pointer=code)
        
        if ctx.integerexpression != None:
            code = ctx.integerexpression.text

            return PAYLOAD(status=Status.OKAY, type=YAPLTypes.INT, code="", pointer=code)

        if ctx.selfexpression != None:
            return PAYLOAD(status=Status.OKAY, type=YAPLTypes.SELF_TYPE, code="", pointer=YAPLTypes.SELF_TYPE)

        return next_expr_res if next_expr_res.type != None else result

    def visitIfx(self, ctx):
        print("STARTING IF EVALUATION")
        if_exp_result = self.visit(ctx.ifexp)
        print("STARTING THEN EVALUATION")
        then_exp_result = self.visit(ctx.thenexp)
        print("STARTING ELSE EVALUATION")
        else_exp_result = self.visit(ctx.elseexp)

        print("IF RESULT HERE <-------")
        print(if_exp_result, then_exp_result, else_exp_result)

        if if_exp_result.type != YAPLTypes.BOOL:
            self.error_list.append("LINE {}: Condicion de if no es de tipo 'Bool'".format(ctx.start.line))
            return PAYLOAD(status=Status.ERROR)

        return_label = self.symbol_table.get_scope().add_label()
        if_label = self.symbol_table.get_scope().add_label()
        else_label = self.symbol_table.get_scope().add_label()

        code = "IF {} GOTO {}\nGOTO {}\n{}:{}\nGOTO {}\n{}:\n{}\n{}:\n".format(if_exp_result.code, if_label, else_label, if_label, then_exp_result.code, return_label, else_label, else_exp_result.code, return_label)

        print(code)

        return PAYLOAD(status=else_exp_result.status, type=else_exp_result.type, code=code)

    def visitWhilex(self, ctx):
        sentence_result = self.visit(ctx.whileSentence)
        action_result = self.visit(ctx.whileAction) 
        
        if sentence_result.type != YAPLTypes.BOOL:
            self.error_list.append("LINE {}: Condicion de while no es de tipo 'Bool'".format(ctx.start.line))
            return PAYLOAD(status=Status.ERROR)

        return action_result

    def visitLetx(self, ctx):
        self.symbol_table.enter_scope("LET_" + str(self.lets + 1))
        for exp in ctx.attribute():
            self.visit(exp)

        print("STARTING LET EVALUATION")
        result = self.visit(ctx.inexp)

        print("LET RESULT", result)

        scopes.append(self.symbol_table.exit_scope())
        return result

    def visitVoidx(self, ctx):
        result = self.visit(ctx.expression())

        return PAYLOAD(status=Status.OKAY, type=YAPLTypes.BOOL) if result.status != Status.ERROR else result

    def visitNotx(self, ctx):
        print("STARTING NOT EXPRESSION EVALUATION")
        result = self.visit(ctx.expression())

        temp_label = self.symbol_table.get_scope().add_temp()

        if result.code == None:
            var = self.symbol_table.lookup(result.name)[1]
            code = "{} = NOT {}[{}] <{}>".format(temp_label, var.get('scope'), var.get('address'), result.name)

        else:
            code = "{}\n{} = NOT {}".format(result.code, temp_label, result.pointer)

        print("CODE", code)

        return PAYLOAD(status=Status.OKAY, type=YAPLTypes.BOOL, code=code, pointer=temp_label) if result.status != Status.ERROR else result

    def visitParenthesisx(self, ctx):
        print("STARTING PARENTHESIS EXPRESSION EVALUATION")
        return self.visit(ctx.expression())

    def visitNegationx(self, ctx):
        print("STARTING NEGATION EXPRESSION EVALUATION")
        result = self.visit(ctx.expression())

        code = "{}\n{} = NOT {}".format(result.code, self.symbol_table.get_scope().add_temp(), result.pointer)

        print("CODE", code)

        print("END NEGATION EXPRESSION EVALUATION")
        return PAYLOAD(status=Status.OKAY, type=YAPLTypes.INT, code=code)

    def visitDeclaration(self, ctx):
        print("START NEW DECLARATION")
        type = ctx.getChild(1).getText()

        if type not in self.type_table.types.keys():
            self.error_list.append("LINE {}: Error al crear instancia de tipo '{}', no reconocido por el sistema".format(ctx.start.line, type))
            return PAYLOAD(status=Status.ERROR)

        code = "{}[0] <self>".format(type)
            
        return PAYLOAD(status=Status.OKAY, type=type, pointer=code)

    def visitMultipleExpr(self, ctx):
        types = []
        code = ""
        for expression in ctx.expression():
            result = self.visit(expression)
            
            types.append(result)
            code += result.code + "\n"

        print("MULTI TYPES", types)

        payload = types[-1]

        return PAYLOAD(status=payload.status, type=payload.type, code=code, pointer=payload.pointer)

    def visitNextOps(self, ctx):
        print("START OPS EVALUATION")
        if ctx.mCall != None:
            payload = self.visit(ctx.mCall)


            method = self.methods_list.get_structure(payload.name)
            name = payload.name
            args = payload.arguments
            code = ""

            for cls in self.classes_list.table:
                for meth in self.classes_list.get_structure(cls).methods:
                    if meth.name == name:
                        class_name = cls
            
            print(class_name)
            print("METHOD CALL PAYLOAD", payload)
            print("METHOD CALL ARGUMENTS", method.args)

            if len(method.args) == len(args):
                print("SAME LENGTH")
                for i in range(len(method.args)):
                    if args[i].status == Status.ERROR:
                        return PAYLOAD(status=Status.ERROR)

                    if args[i].type.upper() == YAPLTypes.SELF_TYPE:
                        args[i] = args[i]._replace(type=list(self.symbol_table.bottom().table.keys())[-1])

                    if method.args[i].get('type') != args[i].type and method.args[i].get('type') != self.type_table.types.get(args[i].type).get('inherits'):
                        print("TRIED TYPES: ", method.args[i].get('type') , args[i])
                        self.error_list.append("LINE {}: Los tipos de los argumentos para el metodo '{}' no concuerdan".format(ctx.start.line, name))
                        return PAYLOAD(status=Status.ERROR)

                    lkp = self.symbol_table.lookup(method.args[i].get('name'))
                    var = lkp[1]

                    print("LOOKUP RESULT", lkp)

                    if lkp[0] == Status.ERROR:
                        print("ARGUMENT NOT FOUND ON SYMBOL TABLE", method.args[i])
                        current_scope = self.symbol_table.get_scope()
                        pointer = current_scope.pointer
                        scope = current_scope.title

                        code += "{}{} [{}] <{}> = {}".format(args[i].code + "\n" if args[i].code != None else "", scope, pointer, lkp[1], args[i].pointer)

                    if args[i].pointer is not None:
                        code += "{}{} [{}] <{}> = {}".format(args[i].code + "\n" if args[i].code != None else "", var.get('scope'), var.get('address'), var.get('name'), args[i].pointer)
                    else:
                        code += "{}[{}] <{}> = {}[{}] <{}>".format(method.args[i].get('scope'), method.args[i].get('address'), method.args[i].get('name'), var.get('scope'), var.get('address'), var.get('name'))

                label = self.symbol_table.get_scope().add_temp()
                code += "\n{} = CALL {}".format(label, name)

                return PAYLOAD(status=Status.OKAY, name=name, type=method.type, code=code, pointer=label)
            else:
                if len(method.args) > len(args):
                    self.error_list.append("LINE {}: El metodo {} recibe {} parametros, se recibieron {}".format(ctx.start.line, name, len(method.args), len(args)))
                    return PAYLOAD(status=Status.ERROR)
                
                if len(method.args) < len(args):
                    self.error_list.append("LINE {}: El metodo {} recibe {} parametros, se recibieron {}".format(ctx.start.line, name, len(method.args), len(args)))
                    return PAYLOAD(status=Status.ERROR)
            

        operator = ctx.getChild(0).getText()
        print(operator, "WILL RETURN")

        if ctx.secondexpression != None:
            expression_result = self.visit(ctx.secondexpression)
            expression_type = expression_result.type

            print("SECOND EXPRESSION WAS", expression_result)

            if expression_type != YAPLTypes.INT:
                self.error_list.append("LINE {}: Los tipos no concuerdan, se intento un tipo '{}' en operacion de 'Int'".format(ctx.start.line, expression_type))
                return PAYLOAD(status=Status.ERROR)

            code = "{} {}".format(operator, expression_result.pointer)

            print("OPERATION CODE IS ", code)

        return PAYLOAD(status=Status.OKAY, type=YAPLTypes.INT, code=expression_result.code, pointer=code) if operator in ['*', '+', '-', '/'] else PAYLOAD(status=Status.OKAY, type=YAPLTypes.BOOL, code=expression_result.code, pointer=code)

    def visitEscexpression(self, ctx):
        print("ENDED IN ESCAPE EXPRESSION")
        return PAYLOAD(status=Status.OKAY, type=None)

    def visitMethodCall(self, ctx):
        print("START METHOD CALL EVALUATION")
        name = ctx.methodName.text

        args = []
        for e in ctx.expression():
            e_type = self.visit(e)
            args.append(e_type)

        return PAYLOAD(status=Status.OKAY, name=name, arguments=args)

    def visitOverwrite(self, ctx):
        print("START OVERWRITE EVALUATION")
        if ctx.attr != None:
            print("START ATTR WRITE EVALUATION")
            actual_type = self.symbol_table.lookup(ctx.name.text)
            if actual_type[0] == Status.ERROR:
                self.error_list.append("LINE {}: La variable '{}' no fue declarada anteriormente".format(ctx.start.line, ctx.name.text))
                return PAYLOAD(status=Status.ERROR)
            
            result = self.visit(ctx.attr)
            declared_type = result.type
            if actual_type[1].get('type') != declared_type:
                self.error_list.append("LINE {}: La asignacion a variable {} no concuerda con tipo {}, se intentó {}".format(ctx.start.line, ctx.name.text, actual_type[1].get('type'), declared_type))
                return PAYLOAD(status=Status.ERROR)
            
            var = self.symbol_table.lookup(ctx.name.text)[1]
            pointer = var.get('address')
            scope = var.get('scope')

            code_to_return = "{}{}[{}] <{}> = {}".format(result.code + "\n" if result.code is not None else "", scope, pointer, ctx.name.text, result.pointer)

            return PAYLOAD(status=Status.OKAY, name=result.name, type=result.type, size=result.size, code=code_to_return, pointer=pointer)
        
        elif ctx.fun != None:
            payload = self.symbol_table.lookup(ctx.name.text)
            print("LOOKUP PAYLOAD", payload)
            if payload[0] == Status.ERROR:
                self.error_list.append("LINE {}: El metodo '{}' no fue declarado anteriormente".format(ctx.start.line, ctx.name.text))
                return PAYLOAD(status=Status.ERROR)

            fun_name = payload[1].get('name')
            method = self.methods_list.get_structure(fun_name)
            args_list = self.visit(ctx.fun)

            code = ""

            print(fun_name)

            if len(method.args) == len(args_list):
                print(method.args, args_list)
                for i in range(len(method.args)):
                    print("ARGS", args_list)
                    print("METHOD ARGS", method.args)
                    if args_list[i].status == Status.ERROR:
                        return PAYLOAD(status=Status.ERROR)

                    if method.args[i].get('type') != args_list[i].type and method.args[i].get('type') != self.type_table.types.get(args_list[i].type).get('inherits'):
                        print("TRIED TYPES: ", method.args[i].get('type') , args_list[i])
                        self.error_list.append("LINE {}: Los tipos de los argumentos para el metodo '{}' no concuerdan".format(ctx.start.line, fun_name))
                        return PAYLOAD(status=Status.ERROR)
                    
                    lkp = self.symbol_table.lookup(method.args[i].get('name'))

                    if lkp[0] == Status.ERROR:
                        print("ARGUMENT NOT FOUND ON SYMBOL TABLE", method.args[i])
                        current_scope = self.symbol_table.get_scope()
                        pointer = current_scope.pointer
                        scope = current_scope.title

                        code += "{}{} [{}] <{}> = {}".format(args_list[i].code + "\n" if args_list[i].code != None else "", scope, pointer, lkp[1], args_list[i].pointer)
                    else:
                        var = lkp[1]
                        code += "{}{} [{}] <{}> = {}".format(args_list[i].code + "\n" if args_list[i].code != None else "", var.get('scope'), var.get('address'), var.get('name'), args_list[i].pointer)

                label = self.symbol_table.get_scope().add_temp()
                code += "\n{} = CALL {}".format(label, fun_name)
                print(method.type)
                return PAYLOAD(status=Status.OKAY, name=ctx.name.text, type=method.type, code=code, pointer=label)
            else:
                if len(method.args) > len(args_list):
                    self.error_list.append("LINE {}:  El metodo {} recibe {} parametros, se recibieron {}".format(ctx.start.line, fun_name, len(method.args), len(args_list)))
                    return PAYLOAD(status=Status.ERROR)
                
                if len(method.args) < len(args_list):
                    self.error_list.append("LINE {}: El metodo {} recibe {} parametros, se recibieron {}".format(ctx.start.line, fun_name, len(method.args), len(args_list)))
                    return PAYLOAD(status=Status.ERROR)
        else:
            payload = self.symbol_table.lookup(ctx.name.text)
            print("NAME", ctx.name.text)
            print("PAYLOAD", payload)
            if payload[0] == Status.ERROR:
                self.error_list.append("LINE {}: La variable '{}' no ha sido declarada".format(ctx.start.line, ctx.name.text))
                return PAYLOAD(status=Status.ERROR)

            print(payload[1].get('type'))
            return PAYLOAD(status=Status.OKAY, name=ctx.name.text, type=payload[1].get('type'))

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
