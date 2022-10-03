grammar YAPLGrammer;

/*PARSER RULES*/
program  
    :   (meat=classP end=';')+;

classP
    :   CLASSKEY className=TYPE (inherits=INHERITSKEY parentClass=TYPE)? '{' features=feature* '}'
;


feature
    :
    (
        fmethod=method
    |   fattribute=attribute
    )
    ';'
;

method
    :   name=ID '(' argumentList=arguments ')' ':' returnType=TYPE '{' mainExpr=expression '}'
;

arguments
    : (formal (',' formal)*)?
;

attribute
    :   name=ID ':' typeName=TYPE ('<-' expression)?
;

formal
    :   name=ID ':' typeName=TYPE
;

expression
    : 
    (
            calls=overwrite
        |   stringexpression=STRING
        |   ifexpression=ifx
        |   whileexpression=whilex
        |   letexpression=letx
        |   newDeclaration=declaration
        |   voidexpression=voidx
        |   notexpression=notx
        |   trueexpression='true'
        |   falseexpression='false' 
        |   parenthesisexpression=parenthesisx
        |   innerexpression=multipleExpr
        |   negationexpression=negationx
        |   integerexpression=INTEGERS
        |   selfexpression=selfx
    ) 
    nextExpr=ops
;

ifx
    : IFKEY ifexp=expression THENKEY thenexp=expression ELSEKEY elseexp=expression FIKEY
;

whilex
    : WHILEKEY whileSentence=expression LOOPKEY whileAction=expression POOLKEY
;

letx
    : LETKEY  letOne=attribute (',' letMore=attribute)* INKEY inexp=expression
;

voidx
    : ISVOIDKEY expression
;

notx
    : NOTKEY expression
;

parenthesisx
    : '(' expression ')'
;

negationx
    : '~' expression
;

declaration
    : NEWKEY TYPE
;

selfx
    : SELFKEY
;

multipleExpr
    : '{' (exp=expression ';')+ '}'
;

ops
    :   
    (
        (
                PLUS
            |   MINUS
            |   STAR 
            |   SLASH 
            |   LOWERTHAN
            |   EQUALS
            |   LOWEREQUAL
        )
        secondexpression=expression
        | mCall = methodCall
    ) ops #nextOps
    | #escexpression
;

methodCall
    : ('@' TYPE)? '.' methodName=ID '(' (expression (',' expression)*)? ')'
;

overwrite
    : 
        name=ID
    (   
        attr=attrWrite
    |   fun=funCall
    |
    )
;

attrWrite
    :
        ('<-' exp=expression)
;

funCall
    :
        '(' (argOne=expression argsMore=more*)? ')'
;

more
    :
    ',' exp=expression
;

/*TOKENS & KEYWORDS*/
fragment A: [aA];
fragment B: [bB];
fragment C: [cC];
fragment D: [dD];
fragment E: [eE];
fragment F: [fF];
fragment G: [gG];
fragment H: [hH];
fragment I: [iI];
fragment J: [jJ];
fragment K: [kK];
fragment L: [lL];
fragment M: [mM];
fragment N: [nN];
fragment O: [oO];
fragment P: [pP];
fragment Q: [qQ];
fragment R: [rR];
fragment S: [sS];
fragment T: [tT];
fragment U: [uU];
fragment V: [vV];
fragment W: [wW];
fragment X: [xX];
fragment Y: [yY];
fragment Z: [zZ];

COMMENTS        : (('--' (ANYSET)* [\n]) | ('(*' (ANYSET)* '*)')) -> skip;

PLUS            : '+';
MINUS           : '-';
STAR            : '*';
SLASH           : '/';
LOWERTHAN       : '<';
EQUALS          : '=';
LOWEREQUAL      : '<=';

IFKEY           : I F;
LOOPKEY         : L O O P;
ELSEKEY         : E L S E;
THENKEY         : T H E N;
WHILEKEY        : W H I L E;
CLASSKEY        : C L A S S;
INHERITSKEY     : I N H E R I T S;
FIKEY           : F I;
INKEY           : I N;
LETKEY          : L E T;
NEWKEY          : N E W;
POOLKEY         : P O O L;
ISVOIDKEY       : I S V O I D;
NOTKEY          : N O T;
SELFKEY         : S E L F;

TYPE            : UPPER(LETTERS|DIGIT|'_')*;

INTEGERS        : DIGIT+;
ID              : (UPPER|LOWER|'_'|DIGIT)+;
OBJECT          : LOWER(LETTERS|DIGIT)+;
ALPHANUMERIC    : (DIGIT|LETTERS);

DIGIT           : [0-9];
LOWER           : [a-z];
UPPER           : [A-Z];

LETTERS         : (LOWER|UPPER);
STRING          : '"' ANYSET* '"';

fragment ANYSET : (LETTERS|DIGIT|'\''|'.'|'('|')'|':'|','|'*'|'-'|'_'|'>'|'?'|'/'|[ \r]|'='|'\\')+;

WHITESPACE      : [ \t\r\n]+ -> skip;