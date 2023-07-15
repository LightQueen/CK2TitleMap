grammar CK;

init : '{'  pair* '}';
array : '{' INT* '}';
pair : VAR '=' val;
val : VAR | STR | init | array;
INT : [0-9.]+[ ];
VAR : [a-zA-Z0-9._]+;
STR : ["][a-zA-Z0-9._']+["];
WS : [ \t\n]+ -> skip;