package com.github.polyrocketmatt.totem.lexical;

/**
 * Created by PolyRocketMatt on 28/09/2020.
 */

public enum TokenType {

    //Literals
    FLOAT_LITERAL,      //  Represents a float
    INT_LITERAL,        //  Represents an integer
    STRING_LITERAL,     //  Represents a string
    BOOL_LITERAL,       //  Represents a bool

    //Symbols
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
    COLON,              //  :
    SEMI_COLON,         //  ;
    DOT,                //  .
    COMMA,              //  ,
    HASH,               //  #

    //Keywords
    FLOAT,              //  float
    INT,                //  int
    STRING,             //  string
    BOOL,               //  boolean
    DEF,                //  def
    USE,                //  import
    NULL,               //  null
    RETURN,             //  return
    FOR,                //  for
    WHILE,              //  while
    IF,                 //  if
    ELSE,               //  else
    ELSE_IF,            //  elseif
    TYPE_OF,            //  typeof
    VOID,               //  void
    PRINT,              //  print
    REPEAT,             //  repeat
    SWAP,               //  swap

    //Last possible option for a token
    IDENTIFIER,         //  Represents an identifier
    COMMENT,            //  Only used when a line starts with //

    SOF,                //  Represents the start of the file
    EOF;                //  Represents the end of the file



}
