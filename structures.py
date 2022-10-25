from collections import namedtuple
from pickle import MEMOIZE
from tkinter.tix import Form
from constants import *
from queue import LifoQueue

class Stack(object):
    """
    CLASS STACK
    
    Esta clase construye un stack utilizando la librería collections y LifoQueue
    e implementa las funcionalidades básicas de un stack (push, pop, get_size, is_empty)
    
    Params:
        None
    """
    def __init__(self):
        self.stack = LifoQueue()
       
        
    def push(self, item):
        self.stack.put(item)
        
    
    def pop(self):
        return self.stack.get()
    
    
    def top(self):
        top = self.pop()
        self.stack.put(top)
        
        return top
    
    
    def snoc(self):
        if self.get_size() == 1:
            return (self.pop(), None)
        
        return (self.pop(), self.top())
    
    
    def get_size(self):
        return self.stack.qsize()
    
    
    def is_empty(self):
        return self.stack.empty()


    def copy(self):
        new = Stack()
        bkp = []
        backup = LifoQueue()

        while not self.stack.empty():
            val = self.stack.get()
            bkp.append(val)

        while len(bkp) > 0:
            val = bkp.pop()
            new.push(val)
            backup.put(val)
        
        self.stack = backup

        return new




class SymbolTable():
    def __init__(self, title):
        self.table = {}
        self.label_counter = 0
        self.pointer = 0
        self.title = title
        self.temp_counter = 0

    
    def empty(self):
        self.table = {}


    def binding(self):
        pass


    def lookup(self, id):
        try:
            return Status.OKAY, self.table[id]
        except:
            return Status.ERROR, id


    def enter(self):
        pass


    def exit(self):
        pass


    def add_label(self):
        self.label_counter += 1
        return "LABEL_{}".format(self.label_counter)
    
    def add_temp(self):
        self.temp_counter += 1
        return "TEMP_{}".format(self.temp_counter)

    
    def get_pointer(self):
        return self.pointer


    def move_pointer(self, offset):
        self.pointer += offset


class SymbolTableStack():
    def __init__(self):
        self.stack = Stack()
        

    def enter_scope(self, title=None):
        self.stack.push(SymbolTable(title))


    def exit_scope(self):
        return self.stack.pop()

    
    def get_scope(self):
        return self.stack.top()


    def lookup(self, id):
        copy = self.stack.copy()

        while copy.get_size() > 0:
            current = copy.top()
            result = current.lookup(id)

            if result[0] == Status.OKAY:
                return result
            else:
                copy.pop()
        
        return Status.ERROR, id


    def empty(self):
        return self.stack.is_empty()

    
    def bottom(self):
        copy = self.stack.copy()

        while copy.get_size() > 1:
            copy.pop()
        
        return copy.pop()

    
    def add_symbol(self, name, type, size):
        current = self.stack.top()

        current.table[name] = {"name": name, "type": type, "size": size, "address": current.pointer, "scope": current.title}

        if size != None: current.move_pointer(size)



class TypeTable():
    def __init__(self) -> None:
        self.types = {
            YAPLTypes.SELF_TYPE : {
                "name": YAPLTypes.SELF_TYPE,
                "inherits": None,
                "size": 0
            },
            YAPLTypes.OBJECT: {
                "name": YAPLTypes.OBJECT,
                "inherits": None,
                "size": 0
            },
            YAPLTypes.INT: {
                "name": YAPLTypes.INT,
                "inherits": YAPLTypes.OBJECT,
                "size": 8
            },
            YAPLTypes.STRING: {
                "name": YAPLTypes.STRING,
                "inherits": YAPLTypes.OBJECT,
                "size": 8
            },
            YAPLTypes.BOOL: {
                "name": YAPLTypes.BOOL,
                "inherits": YAPLTypes.OBJECT,
                "size": 1
            },
            YAPLTypes.IO: {
                "name": YAPLTypes.IO,
                "inherits": YAPLTypes.OBJECT,
                "size": 0
            }
        }


    def add_type(self, typename, size, inherits=None):
        self.types[typename] = {
            "name": typename,
            "inherits": inherits,
            "size": size
        }


    def get_type_size(self, typename):
        return self.types[typename]['size']



class Class():
    def __init__(self, name, attr, meth) -> None:
        self.name = name
        self.attributes = attr
        self.methods = meth



class Method():
    def __init__(self, name, type, args) -> None:
        self.name = name
        self.type = type
        self.args = args



class Attribute():
    def __init__(self, name, type) -> None:
        self.name = name
        self.type = type



class Formal():
    def __init__(self, name, type, scope, address):
        self.name = name
        self.type = type
        self.scope = scope
        self.address = address



class StructureList():
    def __init__(self, st):
        self.structure_type = st
        self.table = {}


    def add_structure(self, args):
        struct = None
        if self.structure_type == YAPLStructures.METHOD:
            struct = Method(args[0], args[1], args[2])
        if self.structure_type == YAPLStructures.CLASS:
            struct = Class(args[0], args[1], args[2])
        if self.structure_type == YAPLStructures.ATTRIBUTE:
            struct = Attribute(args[0], args[1])
        if self.structure_type == YAPLStructures.ARGUMENT:
            struct = Formal(args[0], args[1], args[2], args[3])

        self.table[struct.name] = struct

    
    def get_structure(self, id):
        return self.table[id]

    
    def init_io(self, methods_table):
        out_string = Method("out_string", YAPLTypes.SELF_TYPE, [{"name": "out", "type": YAPLTypes.STRING}])
        out_int = Method("out_int", YAPLTypes.SELF_TYPE, [{"name": "out", "type": YAPLTypes.INT}])
        in_string = Method("in_string", YAPLTypes.STRING, [])
        in_int = Method("in_int", YAPLTypes.INT, [])
        
        self.table["IO"] = Class("IO", None, [out_string, out_int, in_string, in_int])
        
        methods_table.table["out_string"] = out_string
        methods_table.table["out_int"] = out_int
        methods_table.table["in_string"] = in_string
        methods_table.table["in_int"] = in_int

    
    def init_object(self, methods_table):
        abort = Method("abort", YAPLTypes.OBJECT, [])
        type_name = Method("type_name", YAPLTypes.STRING, [])
        copy = Method("copy", YAPLTypes.SELF_TYPE, [])

        self.table["Object"] = Class("Object", None, [abort, type_name, copy])

        methods_table.table["abort"] = abort
        methods_table.table["type_name"] = type_name
        methods_table.table["copy"] = copy
    
    
    def init_string(self, methods_table):
        length = Method("length", YAPLTypes.INT, [])
        concat = Method("concat", YAPLTypes.STRING, [{"name": "s", "type": YAPLTypes.STRING}])
        sub_str = Method("sub_str", YAPLTypes.STRING, [{"name": "i", "type": YAPLTypes.INT}, {"l": "out", "type": YAPLTypes.INT}])

        self.table["String"] = Class("String", None, [length, concat, sub_str])

        methods_table.table["length"] = length
        methods_table.table["concat"] = concat
        methods_table.table["sub_str"] = sub_str



QUADRUPLE = namedtuple('QUADRUPLE', 'operator val1 val2 result')

def create_quadruple(o, a1, a2, r):
    return QUADRUPLE(o, a1, a2, r)

fields = ('status', 'name', 'type', 'arguments', 'size', 'code', 'pointer', 'object')
PAYLOAD = namedtuple('PAYLOAD', fields, defaults=(None,) * len(fields))
