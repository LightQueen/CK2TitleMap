grammar CK;

init : pair*;
pair : VAR '=' val;
map : '{'  pair* '}';
list : '{' map* '}';
array : '{' VAR* '}';
val : VAR | STR | map | list | array;
VAR : [a-zA-Z0-9._\-\ufffd]+;
STR : ["][a-zA-Z0-9._\-\ufffd ']+["];
WS : [ \t\n]+ -> skip;