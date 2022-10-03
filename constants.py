from structures import *

class Colors:
    HEADER     = '\033[95m'
    OKBLUE     = '\033[94m'
    OKCYAN     = '\033[96m'
    OKGREEN    = '\033[92m'
    WARNING    = '\033[93m'
    FAIL       = '\033[91m'
    ENDC       = '\033[0m'
    BOLD       = '\033[1m'
    UNDERLINED = '\033[4m'



class YAPLTypes:
    OBJECT    = "Object"
    INT       = "Int"
    BOOL      = "Bool"
    STRING    = "String"
    IO        = "IO"
    SELF_TYPE = "SELF_TYPE"
    VOID      = "Void"
    TRUE      = "True"
    FALSE     = "False"



class YAPLStructures:
    METHOD    = "Method"
    CLASS     = "Class"
    ATTRIBUTE = "Attribute"
    ARGUMENT  = "Formal"



class Status:
    OKAY  = "Valid"
    ERROR = "Error"
