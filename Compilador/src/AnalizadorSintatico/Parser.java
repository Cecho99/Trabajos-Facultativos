//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
package AnalizadorSintatico;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import AnalizadorLexico.*;
import GeneracionDeCodigo.*;
//#line 24 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ID=257;
public final static short CTE=258;
public final static short INT=259;
public final static short ULONG=260;
public final static short DOUBLE=261;
public final static short CADENA=262;
public final static short IF=263;
public final static short ELSE=264;
public final static short END_IF=265;
public final static short PRINT=266;
public final static short CLASS=267;
public final static short VOID=268;
public final static short DO=269;
public final static short UNTIL=270;
public final static short IMPL=271;
public final static short FOR=272;
public final static short RETURN=273;
public final static short MAYORIGUAL=274;
public final static short MENORIGUAL=275;
public final static short DOBLEIGUAL=276;
public final static short DISTINTO=277;
public final static short MENOSIGUAL=278;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    4,    4,    4,    4,    4,    4,
    8,   10,   11,    7,    7,   12,   13,   13,    5,    9,
   16,   17,   17,   15,   15,   14,   14,   14,    6,    6,
   18,   18,   19,    3,    3,    3,   21,   21,   20,   20,
   20,   20,   20,   20,   20,   20,   20,   20,   20,   20,
   20,   20,   20,   28,   30,   30,   30,   29,   32,   32,
   22,   22,   31,   31,   31,   33,   33,   33,   34,   34,
   34,   23,   23,   24,   24,   35,   35,   35,   38,   38,
   38,   38,   38,   38,   36,   36,   37,   37,   26,   25,
   25,   27,   39,
};
final static short yylen[] = {                            2,
    3,    1,    2,    1,    2,    1,    1,    1,    1,    2,
    2,    3,    4,    2,    2,    2,    3,    5,    2,    2,
    1,    1,    3,    1,    3,    1,    1,    1,    2,    2,
    4,    6,    3,    1,    1,    2,    2,    1,    2,    1,
    2,    2,    1,    2,    1,    2,    1,    2,    1,    2,
    1,    2,    1,    2,    4,    5,    3,    4,    2,    3,
    3,    3,    3,    3,    1,    3,    3,    1,    1,    1,
    2,    4,    3,    4,    5,    5,    4,    4,    1,    1,
    1,    1,    1,    1,    1,    3,    2,    4,    1,    2,
    1,   10,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,   26,   27,   28,    0,    0,
    0,    0,   93,    0,   89,    0,    4,   34,    0,    7,
    8,    9,    0,    0,    0,    0,    0,    0,   35,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   36,    0,
    0,    0,    0,   54,    0,   69,   70,    0,    0,    0,
    0,   68,    0,   90,   16,    0,    0,    1,    3,    5,
   10,    0,   11,    0,   15,   14,   24,    0,   22,    0,
    0,   30,   29,   39,   41,   42,   44,   46,   48,   50,
   52,    0,    0,   73,    0,    0,    0,    0,    0,   71,
   82,   80,   83,   84,    0,    0,   79,   81,    0,    0,
    0,    0,    0,   85,    0,    0,   12,    0,    0,    0,
    0,    0,   38,    0,   72,    0,   57,   60,    0,    0,
    0,    0,    0,   66,   67,    0,    0,   74,    0,   31,
    0,    0,    0,   17,   25,   23,   33,    0,   37,   55,
    0,    0,   78,   86,    0,   87,   75,    0,   13,    0,
    0,   56,   76,    0,   32,   18,    0,   88,    0,    0,
    0,   92,
};
final static short yydgoto[] = {                          2,
    3,   16,   17,   18,   19,   20,   21,   22,   23,   24,
   63,   25,   66,   26,   68,   27,   70,   28,   73,   29,
  114,   30,   31,   32,   33,   34,   35,   36,   37,   44,
   50,   45,   51,   52,   53,  105,  129,   99,   38,
};
final static short yysindex[] = {                      -108,
    0,    0,  372,   -9,  -39,    0,    0,    0,  -21, -245,
 -214, -195,    0, -208,    0,  263,    0,    0,   23,    0,
    0,    0,   30,   18,  -36, -178, -176,  -28,    0,   49,
   53,   56,   64,   69,   75,   76,   77,  -38,    0,  129,
  117, -153,  129,    0,   61,    0,    0,  129, -135,   32,
  -29,    0, -114,    0,    0,   84, -132,    0,    0,    0,
    0,    3,    0,  372,    0,    0,    0,   68,    0,   71,
  372,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -221,  -11,    0,   13,  -20,  -11,  129,   32,    0,
    0,    0,    0,    0,  129,  129,    0,    0,  129,  129,
  129,  -39, -221,    0, -225,  -27,    0, -140,  284, -125,
 -123,  306,    0,  342,    0,  127,    0,    0,  -11,  129,
  -29,  -29,   14,    0,    0,  350, -100,    0, -129,    0,
 -120,   15,  -40,    0,    0,    0,    0, -131,    0,    0,
   20,   41,    0,    0, -221,    0,    0,   97,    0,   19,
  101,    0,    0,  361,    0,    0,  129,    0,   32,  129,
   60,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0, -112,    0,    0,    0,    0,  -42,
    0,    0,    0,    0,    0,    0,    0,    0,  324,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -65,
    0,  139,  161,  187,  205,  224,  243,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -16,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  110,    0,  102,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   54,    0,    0,   87,   72,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   91,    0,
    6,   28,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -112,    0,    0,    0,    0,    0,    0,    0,
    0,  -86,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,  -59,  -13,    0,    0,   48,    0,    0,    0,    0,
    0,    0,    0,   55,    0,    0,    0,    0,    0,  -12,
  -92,    0,    0,    0,    0,    0,    0,    0,    0,   74,
  -10,   78,   16,   17,    0,    0,    0,  -79,    0,
};
final static int YYTABLESIZE=645;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         41,
   41,   91,   59,  150,  109,   42,   42,   65,  103,  120,
  126,  112,  100,  130,    1,   72,   54,  101,   48,  116,
   43,   43,  145,   49,   65,   42,   65,   65,   65,   83,
   85,   95,   87,   96,   39,  102,   77,   89,  127,  128,
  104,    9,   55,   65,   10,   65,   63,   13,   63,   63,
   63,   15,  154,  115,  143,   95,   95,   96,   96,   40,
  152,   56,   95,   57,   96,   63,   60,   63,   64,  113,
   64,   64,   64,   61,   95,   62,   96,  119,   67,  160,
   69,  153,   91,   95,   82,   96,   64,   64,  123,   64,
  113,   97,   74,   98,   71,   59,   75,   62,   59,   76,
  162,  139,   95,   86,   96,  141,   65,   77,   65,  142,
  121,  122,   78,  139,  146,   61,  124,  125,   79,   80,
   81,   88,   90,  106,  107,  108,  110,   12,   63,  111,
   63,  135,  113,  136,   58,  147,  148,  155,  151,  149,
  157,  139,  102,  156,   21,   20,  159,   59,    9,  161,
   64,   10,   64,   19,   13,  132,  102,   84,   15,  117,
  131,   49,    9,  118,    0,   10,    0,  140,   13,    0,
   77,   49,   15,   49,    0,    0,   77,    0,   62,   77,
    0,    0,   77,    0,    0,    0,   77,    0,    0,    0,
   40,   40,    0,   40,   40,   40,   61,   40,   40,   40,
   40,   40,   40,   40,    0,   40,    0,   40,    0,    0,
    0,    0,    0,   91,   91,   58,   91,   91,   91,    0,
   91,   91,   91,   91,   91,   91,   91,    0,   91,    0,
   91,    6,    7,    8,   19,   46,   47,   40,   40,   65,
   65,    0,   65,   65,   65,    0,   65,   65,   65,   65,
   65,   65,   65,    0,   65,    0,   65,   65,   65,   65,
   65,   63,   63,   43,   63,   63,   63,    0,   63,   63,
   63,   63,   63,   63,   63,    0,   63,    0,   63,   63,
   63,   63,   63,   64,   64,   45,   64,   64,   64,    0,
   64,   64,   64,   64,   64,   64,   64,    0,   64,    0,
   64,   64,   64,   64,   64,   91,   92,   93,   94,   62,
   62,   47,   62,   62,   62,    0,   62,   62,   62,   62,
   62,   62,   62,    0,   62,    0,   62,   61,   61,   49,
   61,   61,   61,    0,   61,   61,   61,   61,   61,   61,
   61,    0,   61,    0,   61,    0,   58,   58,   51,   58,
   58,   58,    0,   58,   58,   58,   58,   58,   58,   58,
    0,   58,    0,   58,    0,   19,   19,   53,   19,   19,
   19,    0,   19,   46,   47,   19,   19,   19,   19,    0,
   19,    0,   19,   46,   47,   46,   47,   58,    0,    0,
    0,    0,    0,    0,   43,   43,    0,   43,   43,   43,
    0,   43,   43,   43,   43,   43,   43,   43,  134,   43,
    0,   43,    0,    0,    0,    0,   45,   45,    0,   45,
   45,   45,    0,   45,   45,   45,   45,   45,   45,   45,
  137,   45,    0,   45,    0,    0,    0,    0,    0,    0,
    0,    0,   47,   47,    0,   47,   47,   47,    6,   47,
   47,   47,   47,   47,   47,   47,    0,   47,    0,   47,
   49,   49,    0,   49,   49,   49,  138,   49,   49,   49,
   49,   49,   49,   49,  144,   49,    0,   49,    0,   51,
   51,    0,   51,   51,   51,  158,   51,   51,   51,   51,
   51,   51,   51,    0,   51,    0,   51,    0,   53,   53,
    0,   53,   53,   53,    0,   53,   53,   53,   53,   53,
   53,   53,    0,   53,    0,   53,    0,    0,    4,    5,
    0,    6,    7,    8,    0,    9,    0,    0,   10,   11,
   12,   13,    0,   14,    0,   15,    0,    0,    0,    4,
  133,    0,    6,    7,    8,    0,    9,    0,    0,   10,
   11,   12,   13,    0,   14,    0,   15,    0,    0,    0,
    0,    4,    5,    0,    6,    7,    8,    0,    9,    0,
    0,   10,   11,   12,   13,    0,   14,    0,   15,    6,
    6,    0,    6,    6,    6,    0,    6,    0,    0,    6,
    6,    6,    6,    0,    6,    0,    6,    0,  102,    0,
    0,    0,    0,    0,    9,    0,  102,   10,    0,    0,
   13,    0,    9,    0,   15,   10,    0,  102,   13,    0,
    0,    0,   15,    9,    0,    0,   10,    4,    5,   13,
    6,    7,    8,   15,    9,    0,    0,   10,   11,   12,
   13,    0,   14,    0,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   40,   44,   16,   44,   64,   46,   46,   44,  123,   89,
  103,   71,   42,   41,  123,   44,  262,   47,   40,   40,
   61,   61,  123,   45,   41,   46,   43,   44,   45,   40,
   41,   43,   43,   45,   44,  257,  123,   48,  264,  265,
   53,  263,  257,   60,  266,   62,   41,  269,   43,   44,
   45,  273,  145,   41,   41,   43,   43,   45,   45,  125,
   41,  257,   43,  272,   45,   60,   44,   62,   41,   82,
   43,   44,   45,   44,   43,   58,   45,   88,  257,  159,
  257,   41,  125,   43,  123,   45,  123,   60,   99,   62,
  103,   60,   44,   62,  123,  109,   44,   44,  112,   44,
   41,  114,   43,  257,   45,  116,  123,   44,  125,  120,
   95,   96,   44,  126,  127,   44,  100,  101,   44,   44,
   44,   61,  258,   40,  257,  123,   59,  268,  123,   59,
  125,  257,  145,  257,   44,  265,  257,   41,  270,  125,
   40,  154,  257,  125,  257,   44,  157,   61,  263,  160,
  123,  266,  125,   44,  269,  108,  257,   41,  273,   86,
  106,   45,  263,   86,   -1,  266,   -1,   41,  269,   -1,
  257,   45,  273,   45,   -1,   -1,  263,   -1,  125,  266,
   -1,   -1,  269,   -1,   -1,   -1,  273,   -1,   -1,   -1,
  256,  257,   -1,  259,  260,  261,  125,  263,  264,  265,
  266,  267,  268,  269,   -1,  271,   -1,  273,   -1,   -1,
   -1,   -1,   -1,  256,  257,  125,  259,  260,  261,   -1,
  263,  264,  265,  266,  267,  268,  269,   -1,  271,   -1,
  273,  259,  260,  261,  125,  257,  258,  278,  278,  256,
  257,   -1,  259,  260,  261,   -1,  263,  264,  265,  266,
  267,  268,  269,   -1,  271,   -1,  273,  274,  275,  276,
  277,  256,  257,  125,  259,  260,  261,   -1,  263,  264,
  265,  266,  267,  268,  269,   -1,  271,   -1,  273,  274,
  275,  276,  277,  256,  257,  125,  259,  260,  261,   -1,
  263,  264,  265,  266,  267,  268,  269,   -1,  271,   -1,
  273,  274,  275,  276,  277,  274,  275,  276,  277,  256,
  257,  125,  259,  260,  261,   -1,  263,  264,  265,  266,
  267,  268,  269,   -1,  271,   -1,  273,  256,  257,  125,
  259,  260,  261,   -1,  263,  264,  265,  266,  267,  268,
  269,   -1,  271,   -1,  273,   -1,  256,  257,  125,  259,
  260,  261,   -1,  263,  264,  265,  266,  267,  268,  269,
   -1,  271,   -1,  273,   -1,  256,  257,  125,  259,  260,
  261,   -1,  263,  257,  258,  266,  267,  268,  269,   -1,
  271,   -1,  273,  257,  258,  257,  258,  125,   -1,   -1,
   -1,   -1,   -1,   -1,  256,  257,   -1,  259,  260,  261,
   -1,  263,  264,  265,  266,  267,  268,  269,  125,  271,
   -1,  273,   -1,   -1,   -1,   -1,  256,  257,   -1,  259,
  260,  261,   -1,  263,  264,  265,  266,  267,  268,  269,
  125,  271,   -1,  273,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  256,  257,   -1,  259,  260,  261,  125,  263,
  264,  265,  266,  267,  268,  269,   -1,  271,   -1,  273,
  256,  257,   -1,  259,  260,  261,  125,  263,  264,  265,
  266,  267,  268,  269,  125,  271,   -1,  273,   -1,  256,
  257,   -1,  259,  260,  261,  125,  263,  264,  265,  266,
  267,  268,  269,   -1,  271,   -1,  273,   -1,  256,  257,
   -1,  259,  260,  261,   -1,  263,  264,  265,  266,  267,
  268,  269,   -1,  271,   -1,  273,   -1,   -1,  256,  257,
   -1,  259,  260,  261,   -1,  263,   -1,   -1,  266,  267,
  268,  269,   -1,  271,   -1,  273,   -1,   -1,   -1,  256,
  257,   -1,  259,  260,  261,   -1,  263,   -1,   -1,  266,
  267,  268,  269,   -1,  271,   -1,  273,   -1,   -1,   -1,
   -1,  256,  257,   -1,  259,  260,  261,   -1,  263,   -1,
   -1,  266,  267,  268,  269,   -1,  271,   -1,  273,  256,
  257,   -1,  259,  260,  261,   -1,  263,   -1,   -1,  266,
  267,  268,  269,   -1,  271,   -1,  273,   -1,  257,   -1,
   -1,   -1,   -1,   -1,  263,   -1,  257,  266,   -1,   -1,
  269,   -1,  263,   -1,  273,  266,   -1,  257,  269,   -1,
   -1,   -1,  273,  263,   -1,   -1,  266,  256,  257,  269,
  259,  260,  261,  273,  263,   -1,   -1,  266,  267,  268,
  269,   -1,  271,   -1,  273,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=278;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"ID","CTE","INT","ULONG","DOUBLE","CADENA",
"IF","ELSE","END_IF","PRINT","CLASS","VOID","DO","UNTIL","IMPL","FOR","RETURN",
"MAYORIGUAL","MENORIGUAL","DOBLEIGUAL","DISTINTO","MENOSIGUAL",
};
final static String yyrule[] = {
"$accept : program",
"program : inicio_program bloque '}'",
"inicio_program : '{'",
"bloque : bloque sentencia",
"bloque : sentencia",
"sentencia_declarativa : declaracion_variables ','",
"sentencia_declarativa : declaracion_variables",
"sentencia_declarativa : declaracion_funcion",
"sentencia_declarativa : declaracion_clase",
"sentencia_declarativa : implementacion_metodo",
"sentencia_declarativa : declaracion_instancias_clase ','",
"implementacion_metodo : encabeza_impl cuerpo_impl",
"encabeza_impl : IMPL FOR ID",
"cuerpo_impl : ':' '{' declaracion_funcion '}'",
"declaracion_clase : encabezado_class cuerpo_class",
"declaracion_clase : encabezado_class ','",
"encabezado_class : CLASS ID",
"cuerpo_class : '{' bloque '}'",
"cuerpo_class : '{' bloque ID ',' '}'",
"declaracion_variables : tipo lista_variables",
"declaracion_instancias_clase : tipo_clase lista_instancias",
"tipo_clase : ID",
"lista_instancias : ID",
"lista_instancias : lista_instancias ';' ID",
"lista_variables : ID",
"lista_variables : lista_variables ';' ID",
"tipo : INT",
"tipo : ULONG",
"tipo : DOUBLE",
"declaracion_funcion : encabezado cuerpo_funcion",
"declaracion_funcion : encabezado ','",
"encabezado : VOID ID '(' ')'",
"encabezado : VOID ID '(' tipo ID ')'",
"cuerpo_funcion : '{' bloque '}'",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia : error ','",
"lista_sentencias_ejecutables : lista_sentencias_ejecutables sentencia_ejecutable",
"lista_sentencias_ejecutables : sentencia_ejecutable",
"sentencia_ejecutable : declaracion_asignacion ','",
"sentencia_ejecutable : declaracion_asignacion",
"sentencia_ejecutable : invocacion_funcion ','",
"sentencia_ejecutable : declaracion_if ','",
"sentencia_ejecutable : declaracion_if",
"sentencia_ejecutable : sentencia_print ','",
"sentencia_ejecutable : sentencia_print",
"sentencia_ejecutable : sentencia_return ','",
"sentencia_ejecutable : sentencia_return",
"sentencia_ejecutable : sentencia_do_until ','",
"sentencia_ejecutable : sentencia_do_until",
"sentencia_ejecutable : referencia_a_metodos ','",
"sentencia_ejecutable : referencia_a_metodos",
"sentencia_ejecutable : referencia_a_atributos ','",
"sentencia_ejecutable : referencia_a_atributos",
"referencia_a_metodos : ID met_referenciados",
"met_referenciados : '.' ID '(' ')'",
"met_referenciados : '.' ID '(' expresion_aritmetica ')'",
"met_referenciados : '.' ID met_referenciados",
"referencia_a_atributos : ID atr_referenciados '=' expresion_aritmetica",
"atr_referenciados : '.' ID",
"atr_referenciados : '.' ID atr_referenciados",
"declaracion_asignacion : ID '=' expresion_aritmetica",
"declaracion_asignacion : ID MENOSIGUAL expresion_aritmetica",
"expresion_aritmetica : expresion_aritmetica '+' termino",
"expresion_aritmetica : expresion_aritmetica '-' termino",
"expresion_aritmetica : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : CTE",
"factor : '-' CTE",
"invocacion_funcion : ID '(' expresion_aritmetica ')'",
"invocacion_funcion : ID '(' ')'",
"declaracion_if : IF condicion_if cuerpo_if END_IF",
"declaracion_if : IF condicion_if cuerpo_if cuerpo_else END_IF",
"condicion_if : '(' expresion_aritmetica operador expresion_aritmetica ')'",
"condicion_if : '(' expresion_aritmetica operador expresion_aritmetica",
"condicion_if : expresion_aritmetica operador expresion_aritmetica ')'",
"operador : '<'",
"operador : MENORIGUAL",
"operador : '>'",
"operador : MAYORIGUAL",
"operador : DOBLEIGUAL",
"operador : DISTINTO",
"cuerpo_if : sentencia_ejecutable",
"cuerpo_if : '{' lista_sentencias_ejecutables '}'",
"cuerpo_else : ELSE sentencia_ejecutable",
"cuerpo_else : ELSE '{' lista_sentencias_ejecutables '}'",
"sentencia_return : RETURN",
"sentencia_print : PRINT CADENA",
"sentencia_print : PRINT",
"sentencia_do_until : sentencia_do '{' lista_sentencias_ejecutables '}' UNTIL '(' expresion_aritmetica operador expresion_aritmetica ')'",
"sentencia_do : DO",
};

//#line 208 "gramatica.y"


public static ArrayList<String> erroresLexicos = new ArrayList<String>();

public static ArrayList<String> erroresSintacticos = new ArrayList<String>();

public static ArrayList<String> erroresSemanticos = new ArrayList<String>();

public static String tipoAux = "";

String dirArchivo = "";

String auxID = "";

String atrAux = "";

String auxId2 = "";

String funcAux ="";

String herenciaAux = "";

String ambitoAux ="";

BufferedReader reader;

int token = -1;
	
String claseId = null;
		
public int yylex() throws IOException {
	reader = AnalizadorLexico.getReader();
	while (!AnalizadorLexico.endOfFile(reader)) {
		token = AnalizadorLexico.getToken(reader);
		if (token != -1){
			yylval = new ParserVal(AnalizadorLexico.getToken_actual().toString());
		
			AnalizadorLexico.resetToken();

			return token;
		}
	}
	return token;
}

public void verificarAnidamiento(String simbolo){
	if (TablaDeSimbolos.getClase(simbolo) != null){
		if (TablaDeSimbolos.getAnidamiento(simbolo) > 2 ){
			erroresSemanticos.add("Error en la linea " + AnalizadorLexico.getlinea() + " : se pasa de la cantidad maxima de anidamiento");	
		}
	}
}

public String comprobarAmbito(String simbolo){
	String aux = Ambito.verificarAmbito(simbolo);
	if (!TablaDeSimbolos.getCuerpo(aux)){
		erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getlinea()+" : '" + simbolo + "' no esta al alcance");
		return null;
	}
		if (!TablaDeSimbolos.getImplementar(aux)){
		erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getlinea()+" : '" + simbolo + "' no esta al alcance");
		return null;
	}
	if (aux == null){
		erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getlinea()+" : '" + simbolo + "' no esta al alcance");
	}	
	return aux;
}

public String comprobarAmbitoClase(String simbolo){

	String aux = Ambito.verificarAmbitoClase(simbolo, herenciaAux);
	
	if (aux == null){
		erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getlinea()+" : '" + simbolo + "' no esta al alcance");
	}	
	if (herenciaAux != null){
		String [] a = herenciaAux.split("@");
		for (String spliteado : a ){
			if (TablaDeSimbolos.existeSimbolo(spliteado)){
				TablaDeSimbolos.eliminarToken(spliteado);
			}
		}
	}
	return aux;
}

public void verificarParametros(String simbolo){
	if (TablaDeSimbolos.getParametro(simbolo) != null){
		if (!TablaDeSimbolos.getParametro(simbolo).equals(tipoAux)){
			erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getlinea()+" : los tipos no coinciden ");
		}
	}else{
		erroresSemanticos.add("Error en la linea "+ AnalizadorLexico.getlinea()+" : los tipos no coinciden ");
	}
	
}
private void usoClaseVariable(String lexema){
        String[] parts = Ambito.getAmbitoActual().split("@");
        if (claseId != null && parts.length <=3){
                TablaDeSimbolos.setUso(lexema, "Atributo");
                 if (TablaDeSimbolos.getHerencia(claseId) != null){
                
                         String aux = lexema + "@main:" + TablaDeSimbolos.getHerencia(claseId);
                         if (TablaDeSimbolos.devolverToken(aux) != null){
                                 erroresSemanticos.add("Error : el atributo" + lexema + " ya fue declarado");
                         }
                 }
        }else{
                TablaDeSimbolos.setUso(lexema, "Variable");
        }
}
private void comprobarAtributos(String lexema){
        
        
        if(TablaDeSimbolos.getHerencia(lexema)!=null){
                
                for (String key : TablaDeSimbolos.getTabla().keySet()) {
                    if (TablaDeSimbolos.getClase(key)!= null && TablaDeSimbolos.getUso(key)!= null){
                    
                    
                            if (TablaDeSimbolos.getClase(key).equals(claseId) && TablaDeSimbolos.getUso(key).equals("Atributo")){
                                    
                                    for (String simbolo : TablaDeSimbolos.getTabla().keySet()) {
                                      String padre = TablaDeSimbolos.getHerencia(lexema);
                                      
                                      if (TablaDeSimbolos.getClase(simbolo)!= null && TablaDeSimbolos.getUso(simbolo)!= null){
                                              
                                              String[] partsKey = key.split("@");
                                              String[] partsSimb = simbolo.split("@");
                                              
                                                    if (TablaDeSimbolos.getClase(simbolo).equals(padre) && TablaDeSimbolos.getUso(simbolo).equals("Atributo") && partsKey[0].equals(partsSimb[0]) ){
                                                            erroresSemanticos.add("Error : el simbolo " + key + " ya fue declarado" );
                                                    }
                                            }
                              }
                            }
                    }                                    
            }
        }
}
private void usoClaseFuncion(String lexema){
        String[] parts = Ambito.getAmbitoActual().split("@");
        if (claseId != null && parts.length <=3){
                TablaDeSimbolos.setUso(lexema, "Nombre de metodo");
        }else{
                TablaDeSimbolos.setUso(lexema, "Nombre de funcion");
        }
}
public static void getErrores() {
	System.out.println(" ");
	if (erroresLexicos.size() != 0) {
		System.out.println("ERRORES LEXICOS");
		for (String s : erroresLexicos) {
			System.out.println(s);
		}
	}
	else {
		System.out.println("No hay errores lexicos");
	}
	
	System.out.println(" ");
	System.out.println(" ");
	if (erroresSintacticos.size() != 0) {
		System.out.println("ERRORES SINTACTICOS");
		for (String s : erroresSintacticos) {
			System.out.println(s);
		}
	}
	else {
		System.out.println("No hay errores sintacticos");
	}
	System.out.println(" ");
	System.out.println(" ");
	if (erroresSemanticos.size() != 0) {
		System.out.println("ERRORES SEMANTICOS");
		for (String s : erroresSemanticos) {
			System.out.println(s);
		}
	}
	else {
		System.out.println("No hay errores semanticos");
	}
	
}


private void yyerror(String mensaje) {
	System.out.println("Error yacc: " + mensaje);
}




private void ChequearRangoNegativo(String numNegativo) { 
    String valor = "";
    String aux = numNegativo;
    if (numNegativo.contains("_i")) { 
    	for (int i=0; i < numNegativo.length() ; i++) {
			if (numNegativo.charAt(i) != '_') { 
				valor += numNegativo.charAt(i); 
			}else {
				break;
			}
		}
		int v = Integer.parseInt("-" + valor); 
		if( v < -32768){
			erroresLexicos.add("Error linea " + AnalizadorLexico.getlinea() + " : la constante se pasa del rango minimo ");
		}
		Token tkn = new Token(AnalizadorLexico.getPR("INT"));
	    TablaDeSimbolos.agregarSimbolo("-" + numNegativo, tkn);		
    }
    else {
    	if (numNegativo.contains("_ul")) {
    		for (int i=0; i < numNegativo.length() ; i++) {
				if (numNegativo.charAt(i) != '_') { 
					valor += numNegativo.charAt(i); 
				}else {
					break;
				}
			}
    		
			int v = Integer.parseInt("-" + valor); 
			if( v < 0) {  		
				Parser.erroresLexicos.add("Error linea " + AnalizadorLexico.getlinea() + " : la constante se pasa del rango minimo");
			}
			Token tkn = new Token(AnalizadorLexico.getPR("ULONG"));
		    TablaDeSimbolos.agregarSimbolo(numNegativo, tkn);
    	} 
    	else { 
	    	String valorentero = "";
			String valorexponente = "";
			int tamañoentero = 0;
			
			for (int i = 0; i < numNegativo.length() ; i++) {
				if (numNegativo.charAt(i) == 'D' || numNegativo.charAt(i) == 'd') {
					break;
				}else {
					valorentero += numNegativo.charAt(i);
					tamañoentero++;
				}			
			}
			double ve = Double.parseDouble(valorentero);
		
		
			if (numNegativo.toString().contains("D") || numNegativo.toString().contains("d")) {
				for (int i = tamañoentero + 2; i < numNegativo.length() ; i++) {
					valorexponente += numNegativo.charAt(i);
				}
		
				int vex = Integer.parseInt("-"+ valorexponente);
				double power = Math.pow(ve, vex);
				if(power > Math.pow(AnalizadorLexico.getDoubleMaxLong(), AnalizadorLexico.getDoubleMaxLongExp())) { 
					Parser.erroresLexicos.add("Error linea " + AnalizadorLexico.getlinea() + " : la constante se pasa del rango minimo");
				}
			}
			
			Token tkn = new Token(AnalizadorLexico.getPR("DOUBLE"));
		    TablaDeSimbolos.agregarSimbolo("-" + numNegativo, tkn);
		    TablaDeSimbolos.eliminarToken(valorexponente);
		}
	}
 
}

 
      
//#line 740 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse() throws IOException
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 16 "gramatica.y"
{Ambito.removeAmbito(); Polaca.agregarPolacaMain("Label"); Polaca.agregarPolacaMain("main"); Polaca.agregarPolacaCompleta(Polaca.getPolacaMain());}
break;
case 2:
//#line 19 "gramatica.y"
{Ambito.agregarAmbito("main");}
break;
case 6:
//#line 27 "gramatica.y"
{erroresSintacticos.add("Falta una ',' en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
break;
case 7:
//#line 28 "gramatica.y"
{Polaca.agregarPolacaCompleta(Polaca.getPolaca());}
break;
case 8:
//#line 29 "gramatica.y"
{Ambito.removeAmbito(); claseId = null;}
break;
case 10:
//#line 31 "gramatica.y"
{claseId = null;}
break;
case 12:
//#line 37 "gramatica.y"
{claseId = val_peek(0).sval;ambitoAux = val_peek(0).sval;auxID = comprobarAmbito(val_peek(0).sval); val_peek(0).sval = auxID;}
break;
case 13:
//#line 38 "gramatica.y"
{claseId = null;}
break;
case 15:
//#line 43 "gramatica.y"
{TablaDeSimbolos.setCuerpo(auxID, false);}
break;
case 16:
//#line 46 "gramatica.y"
{claseId = val_peek(0).sval; TablaDeSimbolos.setUso(val_peek(0).sval, "Nombre de clase");auxID = TablaDeSimbolos.setAmbito(val_peek(0).sval);Ambito.agregarAmbito(val_peek(0).sval);val_peek(0).sval = auxID; }
break;
case 17:
//#line 48 "gramatica.y"
{if (auxID != null)TablaDeSimbolos.setCuerpo(auxID, true);}
break;
case 18:
//#line 49 "gramatica.y"
{ TablaDeSimbolos.setHerencia(auxID, val_peek(2).sval); TablaDeSimbolos.setUso(val_peek(2).sval, "Herencia");auxId2 = comprobarAmbito(val_peek(2).sval); val_peek(2).sval = auxId2; if (auxID != null)TablaDeSimbolos.setCuerpo(auxID, true);comprobarAtributos(auxID);}
break;
case 21:
//#line 59 "gramatica.y"
{tipoAux = val_peek(0).sval; auxID = comprobarAmbito(val_peek(0).sval);val_peek(0).sval = auxID;  }
break;
case 22:
//#line 62 "gramatica.y"
{TablaDeSimbolos.setUso(val_peek(0).sval, "Instancia");val_peek(0).sval = TablaDeSimbolos.setAmbito(val_peek(0).sval); TablaDeSimbolos.setTipo(val_peek(0).sval, tipoAux); Polaca.agregarPolaca(val_peek(0).sval);Polaca.agregarPolaca("inst");}
break;
case 23:
//#line 63 "gramatica.y"
{ TablaDeSimbolos.setUso(val_peek(0).sval, "Instancia");val_peek(0).sval = TablaDeSimbolos.setAmbito(val_peek(0).sval); TablaDeSimbolos.setTipo(val_peek(0).sval, tipoAux); Polaca.agregarPolaca(val_peek(0).sval); Polaca.agregarPolaca("inst");}
break;
case 24:
//#line 66 "gramatica.y"
{usoClaseVariable(val_peek(0).sval);val_peek(0).sval = TablaDeSimbolos.setAmbito(val_peek(0).sval); TablaDeSimbolos.setTipo(val_peek(0).sval, tipoAux);TablaDeSimbolos.setClase(val_peek(0).sval, claseId);}
break;
case 25:
//#line 67 "gramatica.y"
{usoClaseVariable(val_peek(0).sval);val_peek(0).sval = TablaDeSimbolos.setAmbito(val_peek(0).sval);TablaDeSimbolos.setTipo(val_peek(0).sval, tipoAux);TablaDeSimbolos.setClase(val_peek(0).sval, claseId);}
break;
case 26:
//#line 71 "gramatica.y"
{tipoAux = val_peek(0).sval;}
break;
case 27:
//#line 72 "gramatica.y"
{tipoAux = val_peek(0).sval;}
break;
case 28:
//#line 73 "gramatica.y"
{tipoAux = val_peek(0).sval;}
break;
case 29:
//#line 76 "gramatica.y"
{Ambito.removeAmbito(); }
break;
case 30:
//#line 77 "gramatica.y"
{TablaDeSimbolos.setImplementar(funcAux, false);Ambito.removeAmbito();TablaDeSimbolos.getTablaSimbolos();funcAux = null;}
break;
case 31:
//#line 80 "gramatica.y"
{  Polaca.agregarPolacaCompleta(Polaca.getPolaca());usoClaseFuncion(val_peek(2).sval); funcAux = TablaDeSimbolos.setAmbito(val_peek(2).sval);Ambito.agregarAmbito(val_peek(2).sval);val_peek(2).sval = funcAux; TablaDeSimbolos.setAnidamiento(val_peek(2).sval); TablaDeSimbolos.setClase(val_peek(2).sval, claseId);verificarAnidamiento(val_peek(2).sval);Polaca.agregarPolaca(val_peek(2).sval); Polaca.agregarPolaca("Label");}
break;
case 32:
//#line 81 "gramatica.y"
{Polaca.agregarPolacaCompleta(Polaca.getPolaca());  TablaDeSimbolos.setUso(val_peek(4).sval, "Nombre de funcion"); funcAux = TablaDeSimbolos.setAmbito(val_peek(4).sval);Ambito.agregarAmbito(val_peek(4).sval);val_peek(4).sval = funcAux; TablaDeSimbolos.setParametro(val_peek(4).sval, tipoAux); TablaDeSimbolos.setUso(val_peek(1).sval, "Parametro");TablaDeSimbolos.setTipo(val_peek(1).sval, tipoAux);auxId2 = TablaDeSimbolos.setAmbito(val_peek(1).sval); val_peek(1).sval = auxId2;TablaDeSimbolos.setClase(val_peek(4).sval, claseId);verificarAnidamiento(val_peek(4).sval); Polaca.agregarPolaca(val_peek(4).sval); Polaca.agregarPolaca("Label");TablaDeSimbolos.setParametroNombre(val_peek(1).sval,val_peek(4).sval);}
break;
case 33:
//#line 87 "gramatica.y"
{TablaDeSimbolos.setImplementar(funcAux, true);}
break;
case 36:
//#line 94 "gramatica.y"
{erroresSintacticos.add("Error en la linea " + Integer.toString(AnalizadorLexico.getlinea() ) );}
break;
case 40:
//#line 102 "gramatica.y"
{erroresSintacticos.add("Falta una ',' en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
break;
case 43:
//#line 105 "gramatica.y"
{erroresSintacticos.add("Falta una ',' en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
break;
case 45:
//#line 107 "gramatica.y"
{erroresSintacticos.add("Falta una ',' en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
break;
case 47:
//#line 109 "gramatica.y"
{erroresSintacticos.add("Falta una ',' en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
break;
case 49:
//#line 111 "gramatica.y"
{erroresSintacticos.add("Falta una ',' en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
break;
case 51:
//#line 113 "gramatica.y"
{erroresSintacticos.add("Falta una ',' en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
break;
case 53:
//#line 115 "gramatica.y"
{erroresSintacticos.add("Falta una ',' en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
break;
case 54:
//#line 118 "gramatica.y"
{auxID = comprobarAmbito(val_peek(1).sval);val_peek(1).sval = auxID;herenciaAux = TablaDeSimbolos.getTipo(val_peek(1).sval) + "@" + herenciaAux; funcAux = comprobarAmbitoClase(funcAux); Polaca.agregarPolaca(val_peek(1).sval); Polaca.agregarPolaca(funcAux); Polaca.agregarPolaca("CALL");herenciaAux = null;}
break;
case 55:
//#line 121 "gramatica.y"
{funcAux = val_peek(2).sval;}
break;
case 56:
//#line 122 "gramatica.y"
{funcAux = val_peek(3).sval;}
break;
case 57:
//#line 123 "gramatica.y"
{herenciaAux = herenciaAux + "@" + val_peek(1).sval ;}
break;
case 58:
//#line 127 "gramatica.y"
{auxID = comprobarAmbito(val_peek(3).sval);val_peek(3).sval = auxID; herenciaAux = TablaDeSimbolos.getTipo(val_peek(3).sval) + "@" + herenciaAux; funcAux = comprobarAmbitoClase(funcAux); Polaca.agregarPolaca(val_peek(3).sval);Polaca.agregarPolaca( funcAux); Polaca.agregarPolaca(val_peek(1).sval);herenciaAux = null;}
break;
case 59:
//#line 131 "gramatica.y"
{funcAux = val_peek(0).sval;}
break;
case 60:
//#line 132 "gramatica.y"
{herenciaAux = herenciaAux + "@" + val_peek(1).sval;}
break;
case 61:
//#line 136 "gramatica.y"
{TablaDeSimbolos.setUso(val_peek(2).sval, "Variable");auxID = comprobarAmbito(val_peek(2).sval); val_peek(2).sval = auxID; Polaca.agregarPolaca(val_peek(2).sval); Polaca.agregarPolaca(val_peek(1).sval);}
break;
case 62:
//#line 137 "gramatica.y"
{TablaDeSimbolos.setUso(val_peek(2).sval, "Variable");auxID = comprobarAmbito(val_peek(2).sval); val_peek(2).sval = auxID; Polaca.agregarPolaca(val_peek(2).sval); Polaca.agregarPolaca("-"); Polaca.agregarPolaca(val_peek(2).sval); Polaca.agregarPolaca("=");}
break;
case 63:
//#line 141 "gramatica.y"
{Polaca.agregarPolaca("+");}
break;
case 64:
//#line 142 "gramatica.y"
{Polaca.agregarPolaca("+");}
break;
case 66:
//#line 146 "gramatica.y"
{Polaca.agregarPolaca("*");}
break;
case 67:
//#line 147 "gramatica.y"
{Polaca.agregarPolaca("/");}
break;
case 69:
//#line 152 "gramatica.y"
{TablaDeSimbolos.setUso(val_peek(0).sval, "Variable");auxID = comprobarAmbito(val_peek(0).sval); val_peek(0).sval = auxID; tipoAux = TablaDeSimbolos.getTipo(val_peek(0).sval); Polaca.agregarPolaca(val_peek(0).sval);  TablaDeSimbolos.setUsadoDerecha(val_peek(0).sval, true);}
break;
case 70:
//#line 153 "gramatica.y"
{TablaDeSimbolos.setUso(val_peek(0).sval, "Constante"); tipoAux = TablaDeSimbolos.getTipo(val_peek(0).sval);Polaca.agregarPolaca(val_peek(0).sval);}
break;
case 71:
//#line 154 "gramatica.y"
{ChequearRangoNegativo(val_peek(0).sval);yyval.sval = "-" + val_peek(0).sval;TablaDeSimbolos.setUso(val_peek(1).sval, "Constante"); tipoAux = TablaDeSimbolos.getTipo(val_peek(0).sval); Polaca.agregarPolaca(val_peek(1).sval);}
break;
case 72:
//#line 157 "gramatica.y"
{auxID = comprobarAmbito(val_peek(3).sval);val_peek(3).sval = auxID;TablaDeSimbolos.noTieneParametros(val_peek(3).sval); verificarParametros(val_peek(3).sval); Polaca.agregarPolaca(val_peek(3).sval); Polaca.agregarPolaca("CALL"); }
break;
case 73:
//#line 158 "gramatica.y"
{auxID = comprobarAmbito(val_peek(2).sval); val_peek(2).sval = auxID;TablaDeSimbolos.tieneParametros(val_peek(2).sval); Polaca.agregarPolaca(val_peek(2).sval); Polaca.agregarPolaca("CALL");}
break;
case 74:
//#line 162 "gramatica.y"
{int posicion = Polaca.pila.pop(); Polaca.polaca.set(posicion, String.valueOf(Polaca.polaca.size()));}
break;
case 75:
//#line 163 "gramatica.y"
{int posicion = Polaca.pila.pop(); Polaca.polaca.set(posicion, String.valueOf(Polaca.polaca.size()));}
break;
case 76:
//#line 166 "gramatica.y"
{Polaca.agregarPolaca(val_peek(2).sval); Polaca.apilar(); Polaca.agregarPolaca(""); Polaca.agregarPolaca("#BF");}
break;
case 77:
//#line 167 "gramatica.y"
{erroresSintacticos.add("Falta un ) en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
break;
case 78:
//#line 168 "gramatica.y"
{erroresSintacticos.add("Falta un ( en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
break;
case 85:
//#line 180 "gramatica.y"
{int posicion = Polaca.pila.pop(); Polaca.polaca.set(posicion, String.valueOf(Polaca.polaca.size() + 4)); Polaca.apilar();  Polaca.agregarPolaca(""); Polaca.agregarPolaca("#BI"); Polaca.agregarPolaca("Label" + String.valueOf(Polaca.polaca.size() + 2 ) + ": "); Polaca.agregarPolaca("#BI");}
break;
case 86:
//#line 181 "gramatica.y"
{int posicion = Polaca.pila.pop(); Polaca.polaca.set(posicion, String.valueOf(Polaca.polaca.size() + 4)); Polaca.apilar();  Polaca.agregarPolaca(""); Polaca.agregarPolaca("#BI"); Polaca.agregarPolaca("Label" + String.valueOf(Polaca.polaca.size() + 2 )+ ": "); Polaca.agregarPolaca("#BI");}
break;
case 87:
//#line 185 "gramatica.y"
{Polaca.agregarPolaca("Label" + String.valueOf(Polaca.polaca.size() + 2) + ": "); Polaca.agregarPolaca("#BI");}
break;
case 88:
//#line 186 "gramatica.y"
{Polaca.agregarPolaca("Label" + String.valueOf(Polaca.polaca.size() + 2)+ ": "); Polaca.agregarPolaca("#BI");}
break;
case 89:
//#line 189 "gramatica.y"
{Polaca.agregarPolaca(val_peek(0).sval);}
break;
case 90:
//#line 192 "gramatica.y"
{Polaca.agregarPolaca(val_peek(0).sval); Polaca.agregarPolaca(val_peek(1).sval);}
break;
case 91:
//#line 194 "gramatica.y"
{erroresSintacticos.add("falta la cadena a imprimir en la linea " + Integer.toString(AnalizadorLexico.getlinea() - 1));}
break;
case 92:
//#line 198 "gramatica.y"
{ Polaca.agregarPolaca(val_peek(2).sval); Polaca.apilar(); int posicion1 = Polaca.pila.pop(); int posicion = Polaca.pila.pop(); Polaca.agregarPolaca(String.valueOf(posicion)); Polaca.agregarPolaca("#BF"); Polaca.agregarPolaca(String.valueOf(posicion1+4)); Polaca.agregarPolaca("#BI"); Polaca.agregarPolaca("Label" + String.valueOf(Polaca.polaca.size()) + ": "); Polaca.agregarPolaca("#BI");}
break;
case 93:
//#line 201 "gramatica.y"
{Polaca.agregarPolaca("Label" + String.valueOf(Polaca.polaca.size()+2) + ": "); Polaca.agregarPolaca("#BI"); Polaca.apilar();}
break;
//#line 1149 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 * @throws IOException 
 */
public void run() throws IOException
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
