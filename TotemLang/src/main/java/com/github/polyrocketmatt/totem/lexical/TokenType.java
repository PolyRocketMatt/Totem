package com.github.polyrocketmatt.totem.lexical;

/**
 * Created by PolyRocketMatt on 28/09/2020.
 *
 * Enum that contains all possible types of
 * tokens recognized by the Tokenizer.
 */

public enum TokenType {

    //  Literals
    FLOAT_LITERAL,      //  Represents a float
    INT_LITERAL,        //  Represents an integer
    STRING_LITERAL,     //  Represents a string
    BOOL_LITERAL,       //  Represents a bool

    //  Symbols
    PLUS,               //  +
    MINUS,              //  -
    ASTERISK,           //  *
    F_SLASH,            //  /
    MODULO,             //  %
    HAT,                //  ^
    TILDE,              //  ~

    OPAREN,             //  (
    CPAREN,             //  )
    OBRACE,             //  {
    CBRACE,             //  }
    OBRACKET,           //  [
    CBRACKET,           //  ]

    EQUAL,              //  =
    EXCLAMATION,        //  !
    LESS_THAN,          //  <
    GREATER_THAN,       //  >
    AMPERSAND,          //  &
    PIPE,               //  |
    COLON,              //  :
    SEMI_COLON,         //  ;
    DOT,                //  .
    COMMA,              //  ,
    HASH,               //  #

    //  Double Tokens
    PRE_POST_INCREMENT, //  ++
    PRE_POST_DECREMENT, //  --
    EQUALS_EQUALS,      //  ==
    LESS_EQUALS,        //  <=
    GREATER_EQUALS,     //  >=
    NOT_EQUALS,         //  !=
    PLUS_EQUALS,        //  +=
    MINUS_EQUALS,       //  -=
    ASTERISK_EQUALS,    //  *=
    F_SLASH_EQUALS,     //  /=
    MODULO_EQUALS,      //  %=
    AND_EQUALS,         //  &=
    OR_EQUALS,          //  |=
    DOUBLE_AMPERSAND,   //  &&
    DOUBLE_PIPE,        //  ||
    DOUBLE_ASTERISK,    //  **
    ARROW,              //  ->s

    //  Keywords
    FLOAT,              //  float
    INT,                //  int
    STRING,             //  string
    BOOL,               //  boolean
    VAR,                //  any type
    DEF,                //  def
    TYPE,               //  type
    USE,                //  import
    NULL,               //  null
    RETURN,             //  return
    FOR,                //  for
    WHILE,              //  while
    IF,                 //  if
    ELSE_IF,            //  elseif
    ELSE,               //  else
    TYPE_OF,            //  typeof
    VOID,               //  void
    PRINT,              //  print
    REPEAT,             //  repeat

    //  Last possible option for a token
    IDENTIFIER,         //  Represents an identifier
    COMMENT,            //  Only used when a line starts with //

    EOF;                //  Represents the end of the file

}
