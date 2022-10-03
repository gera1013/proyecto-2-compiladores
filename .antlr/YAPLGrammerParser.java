// Generated from /Users/gerardomendez/Documents/github/compiladores-proyecto-1/YAPLGrammer.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class YAPLGrammerParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, COMMENTS=14, PLUS=15, MINUS=16, 
		STAR=17, SLASH=18, LOWERTHAN=19, EQUALS=20, LOWEREQUAL=21, IFKEY=22, LOOPKEY=23, 
		ELSEKEY=24, THENKEY=25, WHILEKEY=26, CLASSKEY=27, INHERITSKEY=28, FIKEY=29, 
		INKEY=30, LETKEY=31, NEWKEY=32, POOLKEY=33, ISVOIDKEY=34, NOTKEY=35, SELFKEY=36, 
		TYPE=37, INTEGERS=38, ID=39, OBJECT=40, ALPHANUMERIC=41, DIGIT=42, LOWER=43, 
		UPPER=44, LETTERS=45, STRING=46, WHITESPACE=47;
	public static final int
		RULE_program = 0, RULE_classP = 1, RULE_feature = 2, RULE_method = 3, 
		RULE_arguments = 4, RULE_attribute = 5, RULE_formal = 6, RULE_expression = 7, 
		RULE_ifx = 8, RULE_whilex = 9, RULE_letx = 10, RULE_voidx = 11, RULE_notx = 12, 
		RULE_parenthesisx = 13, RULE_negationx = 14, RULE_declaration = 15, RULE_selfx = 16, 
		RULE_multipleExpr = 17, RULE_ops = 18, RULE_methodCall = 19, RULE_overwrite = 20, 
		RULE_attrWrite = 21, RULE_funCall = 22, RULE_more = 23;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "classP", "feature", "method", "arguments", "attribute", "formal", 
			"expression", "ifx", "whilex", "letx", "voidx", "notx", "parenthesisx", 
			"negationx", "declaration", "selfx", "multipleExpr", "ops", "methodCall", 
			"overwrite", "attrWrite", "funCall", "more"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'{'", "'}'", "'('", "')'", "':'", "','", "'<-'", "'true'", 
			"'false'", "'~'", "'@'", "'.'", null, "'+'", "'-'", "'*'", "'/'", "'<'", 
			"'='", "'<='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "COMMENTS", "PLUS", "MINUS", "STAR", "SLASH", "LOWERTHAN", 
			"EQUALS", "LOWEREQUAL", "IFKEY", "LOOPKEY", "ELSEKEY", "THENKEY", "WHILEKEY", 
			"CLASSKEY", "INHERITSKEY", "FIKEY", "INKEY", "LETKEY", "NEWKEY", "POOLKEY", 
			"ISVOIDKEY", "NOTKEY", "SELFKEY", "TYPE", "INTEGERS", "ID", "OBJECT", 
			"ALPHANUMERIC", "DIGIT", "LOWER", "UPPER", "LETTERS", "STRING", "WHITESPACE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "YAPLGrammer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public YAPLGrammerParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public ClassPContext meat;
		public Token end;
		public List<ClassPContext> classP() {
			return getRuleContexts(ClassPContext.class);
		}
		public ClassPContext classP(int i) {
			return getRuleContext(ClassPContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(48);
				((ProgramContext)_localctx).meat = classP();
				setState(49);
				((ProgramContext)_localctx).end = match(T__0);
				}
				}
				setState(53); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CLASSKEY );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassPContext extends ParserRuleContext {
		public Token className;
		public Token inherits;
		public Token parentClass;
		public FeatureContext features;
		public TerminalNode CLASSKEY() { return getToken(YAPLGrammerParser.CLASSKEY, 0); }
		public List<TerminalNode> TYPE() { return getTokens(YAPLGrammerParser.TYPE); }
		public TerminalNode TYPE(int i) {
			return getToken(YAPLGrammerParser.TYPE, i);
		}
		public TerminalNode INHERITSKEY() { return getToken(YAPLGrammerParser.INHERITSKEY, 0); }
		public List<FeatureContext> feature() {
			return getRuleContexts(FeatureContext.class);
		}
		public FeatureContext feature(int i) {
			return getRuleContext(FeatureContext.class,i);
		}
		public ClassPContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classP; }
	}

	public final ClassPContext classP() throws RecognitionException {
		ClassPContext _localctx = new ClassPContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_classP);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			match(CLASSKEY);
			setState(56);
			((ClassPContext)_localctx).className = match(TYPE);
			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==INHERITSKEY) {
				{
				setState(57);
				((ClassPContext)_localctx).inherits = match(INHERITSKEY);
				setState(58);
				((ClassPContext)_localctx).parentClass = match(TYPE);
				}
			}

			setState(61);
			match(T__1);
			setState(65);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(62);
				((ClassPContext)_localctx).features = feature();
				}
				}
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(68);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FeatureContext extends ParserRuleContext {
		public MethodContext fmethod;
		public AttributeContext fattribute;
		public MethodContext method() {
			return getRuleContext(MethodContext.class,0);
		}
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public FeatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_feature; }
	}

	public final FeatureContext feature() throws RecognitionException {
		FeatureContext _localctx = new FeatureContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_feature);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(70);
				((FeatureContext)_localctx).fmethod = method();
				}
				break;
			case 2:
				{
				setState(71);
				((FeatureContext)_localctx).fattribute = attribute();
				}
				break;
			}
			setState(74);
			match(T__0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodContext extends ParserRuleContext {
		public Token name;
		public ArgumentsContext argumentList;
		public Token returnType;
		public ExpressionContext mainExpr;
		public TerminalNode ID() { return getToken(YAPLGrammerParser.ID, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public TerminalNode TYPE() { return getToken(YAPLGrammerParser.TYPE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MethodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method; }
	}

	public final MethodContext method() throws RecognitionException {
		MethodContext _localctx = new MethodContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_method);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			((MethodContext)_localctx).name = match(ID);
			setState(77);
			match(T__3);
			setState(78);
			((MethodContext)_localctx).argumentList = arguments();
			setState(79);
			match(T__4);
			setState(80);
			match(T__5);
			setState(81);
			((MethodContext)_localctx).returnType = match(TYPE);
			setState(82);
			match(T__1);
			setState(83);
			((MethodContext)_localctx).mainExpr = expression();
			setState(84);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentsContext extends ParserRuleContext {
		public List<FormalContext> formal() {
			return getRuleContexts(FormalContext.class);
		}
		public FormalContext formal(int i) {
			return getRuleContext(FormalContext.class,i);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(86);
				formal();
				setState(91);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(87);
					match(T__6);
					setState(88);
					formal();
					}
					}
					setState(93);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeContext extends ParserRuleContext {
		public Token name;
		public Token typeName;
		public TerminalNode ID() { return getToken(YAPLGrammerParser.ID, 0); }
		public TerminalNode TYPE() { return getToken(YAPLGrammerParser.TYPE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_attribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			((AttributeContext)_localctx).name = match(ID);
			setState(97);
			match(T__5);
			setState(98);
			((AttributeContext)_localctx).typeName = match(TYPE);
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(99);
				match(T__7);
				setState(100);
				expression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormalContext extends ParserRuleContext {
		public Token name;
		public Token typeName;
		public TerminalNode ID() { return getToken(YAPLGrammerParser.ID, 0); }
		public TerminalNode TYPE() { return getToken(YAPLGrammerParser.TYPE, 0); }
		public FormalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formal; }
	}

	public final FormalContext formal() throws RecognitionException {
		FormalContext _localctx = new FormalContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_formal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			((FormalContext)_localctx).name = match(ID);
			setState(104);
			match(T__5);
			setState(105);
			((FormalContext)_localctx).typeName = match(TYPE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public OverwriteContext calls;
		public Token stringexpression;
		public IfxContext ifexpression;
		public WhilexContext whileexpression;
		public LetxContext letexpression;
		public DeclarationContext newDeclaration;
		public VoidxContext voidexpression;
		public NotxContext notexpression;
		public Token trueexpression;
		public Token falseexpression;
		public ParenthesisxContext parenthesisexpression;
		public MultipleExprContext innerexpression;
		public NegationxContext negationexpression;
		public Token integerexpression;
		public SelfxContext selfexpression;
		public OpsContext nextExpr;
		public OpsContext ops() {
			return getRuleContext(OpsContext.class,0);
		}
		public OverwriteContext overwrite() {
			return getRuleContext(OverwriteContext.class,0);
		}
		public TerminalNode STRING() { return getToken(YAPLGrammerParser.STRING, 0); }
		public IfxContext ifx() {
			return getRuleContext(IfxContext.class,0);
		}
		public WhilexContext whilex() {
			return getRuleContext(WhilexContext.class,0);
		}
		public LetxContext letx() {
			return getRuleContext(LetxContext.class,0);
		}
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public VoidxContext voidx() {
			return getRuleContext(VoidxContext.class,0);
		}
		public NotxContext notx() {
			return getRuleContext(NotxContext.class,0);
		}
		public ParenthesisxContext parenthesisx() {
			return getRuleContext(ParenthesisxContext.class,0);
		}
		public MultipleExprContext multipleExpr() {
			return getRuleContext(MultipleExprContext.class,0);
		}
		public NegationxContext negationx() {
			return getRuleContext(NegationxContext.class,0);
		}
		public TerminalNode INTEGERS() { return getToken(YAPLGrammerParser.INTEGERS, 0); }
		public SelfxContext selfx() {
			return getRuleContext(SelfxContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(107);
				((ExpressionContext)_localctx).calls = overwrite();
				}
				break;
			case STRING:
				{
				setState(108);
				((ExpressionContext)_localctx).stringexpression = match(STRING);
				}
				break;
			case IFKEY:
				{
				setState(109);
				((ExpressionContext)_localctx).ifexpression = ifx();
				}
				break;
			case WHILEKEY:
				{
				setState(110);
				((ExpressionContext)_localctx).whileexpression = whilex();
				}
				break;
			case LETKEY:
				{
				setState(111);
				((ExpressionContext)_localctx).letexpression = letx();
				}
				break;
			case NEWKEY:
				{
				setState(112);
				((ExpressionContext)_localctx).newDeclaration = declaration();
				}
				break;
			case ISVOIDKEY:
				{
				setState(113);
				((ExpressionContext)_localctx).voidexpression = voidx();
				}
				break;
			case NOTKEY:
				{
				setState(114);
				((ExpressionContext)_localctx).notexpression = notx();
				}
				break;
			case T__8:
				{
				setState(115);
				((ExpressionContext)_localctx).trueexpression = match(T__8);
				}
				break;
			case T__9:
				{
				setState(116);
				((ExpressionContext)_localctx).falseexpression = match(T__9);
				}
				break;
			case T__3:
				{
				setState(117);
				((ExpressionContext)_localctx).parenthesisexpression = parenthesisx();
				}
				break;
			case T__1:
				{
				setState(118);
				((ExpressionContext)_localctx).innerexpression = multipleExpr();
				}
				break;
			case T__10:
				{
				setState(119);
				((ExpressionContext)_localctx).negationexpression = negationx();
				}
				break;
			case INTEGERS:
				{
				setState(120);
				((ExpressionContext)_localctx).integerexpression = match(INTEGERS);
				}
				break;
			case SELFKEY:
				{
				setState(121);
				((ExpressionContext)_localctx).selfexpression = selfx();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(124);
			((ExpressionContext)_localctx).nextExpr = ops();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfxContext extends ParserRuleContext {
		public ExpressionContext ifexp;
		public ExpressionContext thenexp;
		public ExpressionContext elseexp;
		public TerminalNode IFKEY() { return getToken(YAPLGrammerParser.IFKEY, 0); }
		public TerminalNode THENKEY() { return getToken(YAPLGrammerParser.THENKEY, 0); }
		public TerminalNode ELSEKEY() { return getToken(YAPLGrammerParser.ELSEKEY, 0); }
		public TerminalNode FIKEY() { return getToken(YAPLGrammerParser.FIKEY, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public IfxContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifx; }
	}

	public final IfxContext ifx() throws RecognitionException {
		IfxContext _localctx = new IfxContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_ifx);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			match(IFKEY);
			setState(127);
			((IfxContext)_localctx).ifexp = expression();
			setState(128);
			match(THENKEY);
			setState(129);
			((IfxContext)_localctx).thenexp = expression();
			setState(130);
			match(ELSEKEY);
			setState(131);
			((IfxContext)_localctx).elseexp = expression();
			setState(132);
			match(FIKEY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhilexContext extends ParserRuleContext {
		public ExpressionContext whileSentence;
		public ExpressionContext whileAction;
		public TerminalNode WHILEKEY() { return getToken(YAPLGrammerParser.WHILEKEY, 0); }
		public TerminalNode LOOPKEY() { return getToken(YAPLGrammerParser.LOOPKEY, 0); }
		public TerminalNode POOLKEY() { return getToken(YAPLGrammerParser.POOLKEY, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public WhilexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whilex; }
	}

	public final WhilexContext whilex() throws RecognitionException {
		WhilexContext _localctx = new WhilexContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_whilex);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			match(WHILEKEY);
			setState(135);
			((WhilexContext)_localctx).whileSentence = expression();
			setState(136);
			match(LOOPKEY);
			setState(137);
			((WhilexContext)_localctx).whileAction = expression();
			setState(138);
			match(POOLKEY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LetxContext extends ParserRuleContext {
		public AttributeContext letOne;
		public AttributeContext letMore;
		public ExpressionContext inexp;
		public TerminalNode LETKEY() { return getToken(YAPLGrammerParser.LETKEY, 0); }
		public TerminalNode INKEY() { return getToken(YAPLGrammerParser.INKEY, 0); }
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LetxContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_letx; }
	}

	public final LetxContext letx() throws RecognitionException {
		LetxContext _localctx = new LetxContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_letx);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			match(LETKEY);
			setState(141);
			((LetxContext)_localctx).letOne = attribute();
			setState(146);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(142);
				match(T__6);
				setState(143);
				((LetxContext)_localctx).letMore = attribute();
				}
				}
				setState(148);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(149);
			match(INKEY);
			setState(150);
			((LetxContext)_localctx).inexp = expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VoidxContext extends ParserRuleContext {
		public TerminalNode ISVOIDKEY() { return getToken(YAPLGrammerParser.ISVOIDKEY, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VoidxContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_voidx; }
	}

	public final VoidxContext voidx() throws RecognitionException {
		VoidxContext _localctx = new VoidxContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_voidx);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			match(ISVOIDKEY);
			setState(153);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NotxContext extends ParserRuleContext {
		public TerminalNode NOTKEY() { return getToken(YAPLGrammerParser.NOTKEY, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NotxContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_notx; }
	}

	public final NotxContext notx() throws RecognitionException {
		NotxContext _localctx = new NotxContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_notx);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			match(NOTKEY);
			setState(156);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParenthesisxContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParenthesisxContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parenthesisx; }
	}

	public final ParenthesisxContext parenthesisx() throws RecognitionException {
		ParenthesisxContext _localctx = new ParenthesisxContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_parenthesisx);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			match(T__3);
			setState(159);
			expression();
			setState(160);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NegationxContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NegationxContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_negationx; }
	}

	public final NegationxContext negationx() throws RecognitionException {
		NegationxContext _localctx = new NegationxContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_negationx);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			match(T__10);
			setState(163);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public TerminalNode NEWKEY() { return getToken(YAPLGrammerParser.NEWKEY, 0); }
		public TerminalNode TYPE() { return getToken(YAPLGrammerParser.TYPE, 0); }
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			match(NEWKEY);
			setState(166);
			match(TYPE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelfxContext extends ParserRuleContext {
		public TerminalNode SELFKEY() { return getToken(YAPLGrammerParser.SELFKEY, 0); }
		public SelfxContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selfx; }
	}

	public final SelfxContext selfx() throws RecognitionException {
		SelfxContext _localctx = new SelfxContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_selfx);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			match(SELFKEY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultipleExprContext extends ParserRuleContext {
		public ExpressionContext exp;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public MultipleExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multipleExpr; }
	}

	public final MultipleExprContext multipleExpr() throws RecognitionException {
		MultipleExprContext _localctx = new MultipleExprContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_multipleExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			match(T__1);
			setState(174); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(171);
				((MultipleExprContext)_localctx).exp = expression();
				setState(172);
				match(T__0);
				}
				}
				setState(176); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__3) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << IFKEY) | (1L << WHILEKEY) | (1L << LETKEY) | (1L << NEWKEY) | (1L << ISVOIDKEY) | (1L << NOTKEY) | (1L << SELFKEY) | (1L << INTEGERS) | (1L << ID) | (1L << STRING))) != 0) );
			setState(178);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OpsContext extends ParserRuleContext {
		public OpsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ops; }
	 
		public OpsContext() { }
		public void copyFrom(OpsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class EscexpressionContext extends OpsContext {
		public EscexpressionContext(OpsContext ctx) { copyFrom(ctx); }
	}
	public static class NextOpsContext extends OpsContext {
		public ExpressionContext secondexpression;
		public MethodCallContext mCall;
		public OpsContext ops() {
			return getRuleContext(OpsContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(YAPLGrammerParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(YAPLGrammerParser.MINUS, 0); }
		public TerminalNode STAR() { return getToken(YAPLGrammerParser.STAR, 0); }
		public TerminalNode SLASH() { return getToken(YAPLGrammerParser.SLASH, 0); }
		public TerminalNode LOWERTHAN() { return getToken(YAPLGrammerParser.LOWERTHAN, 0); }
		public TerminalNode EQUALS() { return getToken(YAPLGrammerParser.EQUALS, 0); }
		public TerminalNode LOWEREQUAL() { return getToken(YAPLGrammerParser.LOWEREQUAL, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MethodCallContext methodCall() {
			return getRuleContext(MethodCallContext.class,0);
		}
		public NextOpsContext(OpsContext ctx) { copyFrom(ctx); }
	}

	public final OpsContext ops() throws RecognitionException {
		OpsContext _localctx = new OpsContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_ops);
		int _la;
		try {
			setState(188);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				_localctx = new NextOpsContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(183);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case PLUS:
				case MINUS:
				case STAR:
				case SLASH:
				case LOWERTHAN:
				case EQUALS:
				case LOWEREQUAL:
					{
					setState(180);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PLUS) | (1L << MINUS) | (1L << STAR) | (1L << SLASH) | (1L << LOWERTHAN) | (1L << EQUALS) | (1L << LOWEREQUAL))) != 0)) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(181);
					((NextOpsContext)_localctx).secondexpression = expression();
					}
					break;
				case T__11:
				case T__12:
					{
					setState(182);
					((NextOpsContext)_localctx).mCall = methodCall();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(185);
				ops();
				}
				break;
			case 2:
				_localctx = new EscexpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodCallContext extends ParserRuleContext {
		public Token methodName;
		public TerminalNode ID() { return getToken(YAPLGrammerParser.ID, 0); }
		public TerminalNode TYPE() { return getToken(YAPLGrammerParser.TYPE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public MethodCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodCall; }
	}

	public final MethodCallContext methodCall() throws RecognitionException {
		MethodCallContext _localctx = new MethodCallContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_methodCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(190);
				match(T__11);
				setState(191);
				match(TYPE);
				}
			}

			setState(194);
			match(T__12);
			setState(195);
			((MethodCallContext)_localctx).methodName = match(ID);
			setState(196);
			match(T__3);
			setState(205);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__3) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << IFKEY) | (1L << WHILEKEY) | (1L << LETKEY) | (1L << NEWKEY) | (1L << ISVOIDKEY) | (1L << NOTKEY) | (1L << SELFKEY) | (1L << INTEGERS) | (1L << ID) | (1L << STRING))) != 0)) {
				{
				setState(197);
				expression();
				setState(202);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(198);
					match(T__6);
					setState(199);
					expression();
					}
					}
					setState(204);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(207);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OverwriteContext extends ParserRuleContext {
		public Token name;
		public AttrWriteContext attr;
		public FunCallContext fun;
		public TerminalNode ID() { return getToken(YAPLGrammerParser.ID, 0); }
		public AttrWriteContext attrWrite() {
			return getRuleContext(AttrWriteContext.class,0);
		}
		public FunCallContext funCall() {
			return getRuleContext(FunCallContext.class,0);
		}
		public OverwriteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_overwrite; }
	}

	public final OverwriteContext overwrite() throws RecognitionException {
		OverwriteContext _localctx = new OverwriteContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_overwrite);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			((OverwriteContext)_localctx).name = match(ID);
			setState(213);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
				{
				setState(210);
				((OverwriteContext)_localctx).attr = attrWrite();
				}
				break;
			case T__3:
				{
				setState(211);
				((OverwriteContext)_localctx).fun = funCall();
				}
				break;
			case T__0:
			case T__2:
			case T__4:
			case T__6:
			case T__11:
			case T__12:
			case PLUS:
			case MINUS:
			case STAR:
			case SLASH:
			case LOWERTHAN:
			case EQUALS:
			case LOWEREQUAL:
			case LOOPKEY:
			case ELSEKEY:
			case THENKEY:
			case FIKEY:
			case INKEY:
			case POOLKEY:
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttrWriteContext extends ParserRuleContext {
		public ExpressionContext exp;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AttrWriteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attrWrite; }
	}

	public final AttrWriteContext attrWrite() throws RecognitionException {
		AttrWriteContext _localctx = new AttrWriteContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_attrWrite);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(215);
			match(T__7);
			setState(216);
			((AttrWriteContext)_localctx).exp = expression();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunCallContext extends ParserRuleContext {
		public ExpressionContext argOne;
		public MoreContext argsMore;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<MoreContext> more() {
			return getRuleContexts(MoreContext.class);
		}
		public MoreContext more(int i) {
			return getRuleContext(MoreContext.class,i);
		}
		public FunCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funCall; }
	}

	public final FunCallContext funCall() throws RecognitionException {
		FunCallContext _localctx = new FunCallContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_funCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			match(T__3);
			setState(226);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__3) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << IFKEY) | (1L << WHILEKEY) | (1L << LETKEY) | (1L << NEWKEY) | (1L << ISVOIDKEY) | (1L << NOTKEY) | (1L << SELFKEY) | (1L << INTEGERS) | (1L << ID) | (1L << STRING))) != 0)) {
				{
				setState(219);
				((FunCallContext)_localctx).argOne = expression();
				setState(223);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(220);
					((FunCallContext)_localctx).argsMore = more();
					}
					}
					setState(225);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(228);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MoreContext extends ParserRuleContext {
		public ExpressionContext exp;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MoreContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_more; }
	}

	public final MoreContext more() throws RecognitionException {
		MoreContext _localctx = new MoreContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_more);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(230);
			match(T__6);
			setState(231);
			((MoreContext)_localctx).exp = expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\61\u00ec\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\3\2\3\2\3\2\6\2\66\n\2\r\2\16\2\67\3\3\3\3\3\3\3\3\5\3>\n\3\3\3\3\3\7"+
		"\3B\n\3\f\3\16\3E\13\3\3\3\3\3\3\4\3\4\5\4K\n\4\3\4\3\4\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\7\6\\\n\6\f\6\16\6_\13\6\5\6a\n"+
		"\6\3\7\3\7\3\7\3\7\3\7\5\7h\n\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t}\n\t\3\t\3\t\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\7\f\u0093"+
		"\n\f\f\f\16\f\u0096\13\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3"+
		"\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3"+
		"\23\6\23\u00b1\n\23\r\23\16\23\u00b2\3\23\3\23\3\24\3\24\3\24\5\24\u00ba"+
		"\n\24\3\24\3\24\3\24\5\24\u00bf\n\24\3\25\3\25\5\25\u00c3\n\25\3\25\3"+
		"\25\3\25\3\25\3\25\3\25\7\25\u00cb\n\25\f\25\16\25\u00ce\13\25\5\25\u00d0"+
		"\n\25\3\25\3\25\3\26\3\26\3\26\3\26\5\26\u00d8\n\26\3\27\3\27\3\27\3\30"+
		"\3\30\3\30\7\30\u00e0\n\30\f\30\16\30\u00e3\13\30\5\30\u00e5\n\30\3\30"+
		"\3\30\3\31\3\31\3\31\3\31\2\2\32\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"+
		" \"$&(*,.\60\2\3\3\2\21\27\2\u00f3\2\65\3\2\2\2\49\3\2\2\2\6J\3\2\2\2"+
		"\bN\3\2\2\2\n`\3\2\2\2\fb\3\2\2\2\16i\3\2\2\2\20|\3\2\2\2\22\u0080\3\2"+
		"\2\2\24\u0088\3\2\2\2\26\u008e\3\2\2\2\30\u009a\3\2\2\2\32\u009d\3\2\2"+
		"\2\34\u00a0\3\2\2\2\36\u00a4\3\2\2\2 \u00a7\3\2\2\2\"\u00aa\3\2\2\2$\u00ac"+
		"\3\2\2\2&\u00be\3\2\2\2(\u00c2\3\2\2\2*\u00d3\3\2\2\2,\u00d9\3\2\2\2."+
		"\u00dc\3\2\2\2\60\u00e8\3\2\2\2\62\63\5\4\3\2\63\64\7\3\2\2\64\66\3\2"+
		"\2\2\65\62\3\2\2\2\66\67\3\2\2\2\67\65\3\2\2\2\678\3\2\2\28\3\3\2\2\2"+
		"9:\7\35\2\2:=\7\'\2\2;<\7\36\2\2<>\7\'\2\2=;\3\2\2\2=>\3\2\2\2>?\3\2\2"+
		"\2?C\7\4\2\2@B\5\6\4\2A@\3\2\2\2BE\3\2\2\2CA\3\2\2\2CD\3\2\2\2DF\3\2\2"+
		"\2EC\3\2\2\2FG\7\5\2\2G\5\3\2\2\2HK\5\b\5\2IK\5\f\7\2JH\3\2\2\2JI\3\2"+
		"\2\2KL\3\2\2\2LM\7\3\2\2M\7\3\2\2\2NO\7)\2\2OP\7\6\2\2PQ\5\n\6\2QR\7\7"+
		"\2\2RS\7\b\2\2ST\7\'\2\2TU\7\4\2\2UV\5\20\t\2VW\7\5\2\2W\t\3\2\2\2X]\5"+
		"\16\b\2YZ\7\t\2\2Z\\\5\16\b\2[Y\3\2\2\2\\_\3\2\2\2][\3\2\2\2]^\3\2\2\2"+
		"^a\3\2\2\2_]\3\2\2\2`X\3\2\2\2`a\3\2\2\2a\13\3\2\2\2bc\7)\2\2cd\7\b\2"+
		"\2dg\7\'\2\2ef\7\n\2\2fh\5\20\t\2ge\3\2\2\2gh\3\2\2\2h\r\3\2\2\2ij\7)"+
		"\2\2jk\7\b\2\2kl\7\'\2\2l\17\3\2\2\2m}\5*\26\2n}\7\60\2\2o}\5\22\n\2p"+
		"}\5\24\13\2q}\5\26\f\2r}\5 \21\2s}\5\30\r\2t}\5\32\16\2u}\7\13\2\2v}\7"+
		"\f\2\2w}\5\34\17\2x}\5$\23\2y}\5\36\20\2z}\7(\2\2{}\5\"\22\2|m\3\2\2\2"+
		"|n\3\2\2\2|o\3\2\2\2|p\3\2\2\2|q\3\2\2\2|r\3\2\2\2|s\3\2\2\2|t\3\2\2\2"+
		"|u\3\2\2\2|v\3\2\2\2|w\3\2\2\2|x\3\2\2\2|y\3\2\2\2|z\3\2\2\2|{\3\2\2\2"+
		"}~\3\2\2\2~\177\5&\24\2\177\21\3\2\2\2\u0080\u0081\7\30\2\2\u0081\u0082"+
		"\5\20\t\2\u0082\u0083\7\33\2\2\u0083\u0084\5\20\t\2\u0084\u0085\7\32\2"+
		"\2\u0085\u0086\5\20\t\2\u0086\u0087\7\37\2\2\u0087\23\3\2\2\2\u0088\u0089"+
		"\7\34\2\2\u0089\u008a\5\20\t\2\u008a\u008b\7\31\2\2\u008b\u008c\5\20\t"+
		"\2\u008c\u008d\7#\2\2\u008d\25\3\2\2\2\u008e\u008f\7!\2\2\u008f\u0094"+
		"\5\f\7\2\u0090\u0091\7\t\2\2\u0091\u0093\5\f\7\2\u0092\u0090\3\2\2\2\u0093"+
		"\u0096\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0097\3\2"+
		"\2\2\u0096\u0094\3\2\2\2\u0097\u0098\7 \2\2\u0098\u0099\5\20\t\2\u0099"+
		"\27\3\2\2\2\u009a\u009b\7$\2\2\u009b\u009c\5\20\t\2\u009c\31\3\2\2\2\u009d"+
		"\u009e\7%\2\2\u009e\u009f\5\20\t\2\u009f\33\3\2\2\2\u00a0\u00a1\7\6\2"+
		"\2\u00a1\u00a2\5\20\t\2\u00a2\u00a3\7\7\2\2\u00a3\35\3\2\2\2\u00a4\u00a5"+
		"\7\r\2\2\u00a5\u00a6\5\20\t\2\u00a6\37\3\2\2\2\u00a7\u00a8\7\"\2\2\u00a8"+
		"\u00a9\7\'\2\2\u00a9!\3\2\2\2\u00aa\u00ab\7&\2\2\u00ab#\3\2\2\2\u00ac"+
		"\u00b0\7\4\2\2\u00ad\u00ae\5\20\t\2\u00ae\u00af\7\3\2\2\u00af\u00b1\3"+
		"\2\2\2\u00b0\u00ad\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b2"+
		"\u00b3\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b5\7\5\2\2\u00b5%\3\2\2\2"+
		"\u00b6\u00b7\t\2\2\2\u00b7\u00ba\5\20\t\2\u00b8\u00ba\5(\25\2\u00b9\u00b6"+
		"\3\2\2\2\u00b9\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00bc\5&\24\2\u00bc"+
		"\u00bf\3\2\2\2\u00bd\u00bf\3\2\2\2\u00be\u00b9\3\2\2\2\u00be\u00bd\3\2"+
		"\2\2\u00bf\'\3\2\2\2\u00c0\u00c1\7\16\2\2\u00c1\u00c3\7\'\2\2\u00c2\u00c0"+
		"\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c5\7\17\2\2"+
		"\u00c5\u00c6\7)\2\2\u00c6\u00cf\7\6\2\2\u00c7\u00cc\5\20\t\2\u00c8\u00c9"+
		"\7\t\2\2\u00c9\u00cb\5\20\t\2\u00ca\u00c8\3\2\2\2\u00cb\u00ce\3\2\2\2"+
		"\u00cc\u00ca\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\u00d0\3\2\2\2\u00ce\u00cc"+
		"\3\2\2\2\u00cf\u00c7\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1"+
		"\u00d2\7\7\2\2\u00d2)\3\2\2\2\u00d3\u00d7\7)\2\2\u00d4\u00d8\5,\27\2\u00d5"+
		"\u00d8\5.\30\2\u00d6\u00d8\3\2\2\2\u00d7\u00d4\3\2\2\2\u00d7\u00d5\3\2"+
		"\2\2\u00d7\u00d6\3\2\2\2\u00d8+\3\2\2\2\u00d9\u00da\7\n\2\2\u00da\u00db"+
		"\5\20\t\2\u00db-\3\2\2\2\u00dc\u00e4\7\6\2\2\u00dd\u00e1\5\20\t\2\u00de"+
		"\u00e0\5\60\31\2\u00df\u00de\3\2\2\2\u00e0\u00e3\3\2\2\2\u00e1\u00df\3"+
		"\2\2\2\u00e1\u00e2\3\2\2\2\u00e2\u00e5\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e4"+
		"\u00dd\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e7\7\7"+
		"\2\2\u00e7/\3\2\2\2\u00e8\u00e9\7\t\2\2\u00e9\u00ea\5\20\t\2\u00ea\61"+
		"\3\2\2\2\24\67=CJ]`g|\u0094\u00b2\u00b9\u00be\u00c2\u00cc\u00cf\u00d7"+
		"\u00e1\u00e4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}