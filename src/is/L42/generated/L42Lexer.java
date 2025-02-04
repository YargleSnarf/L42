// Generated from L42.g4 by ANTLR 4.7.2
package is.L42.generated;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class L42Lexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, CastOp=8, Uop=9, 
		OP0=10, OP1=11, OP2=12, OP3=13, OpUpdate=14, Mdf=15, VoidKW=16, VarKw=17, 
		Info=18, CatchKw=19, InterfaceKw=20, IfKw=21, ElseKw=22, WhileKw=23, ForKw=24, 
		InKw=25, LoopKw=26, Throw=27, WhoopsKw=28, MethodKw=29, DotDotDot=30, 
		Slash=31, PathSel=32, ReuseURL=33, NativeURL=34, StringMulti=35, StringSingle=36, 
		Number=37, MUniqueNum=38, MHash=39, X=40, SlashX=41, CsP=42, ClassSep=43, 
		UnderScore=44, OR=45, ORNS=46, Doc=47, BlockComment=48, LineComment=49, 
		Whitespace=50;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "CastOp", "Uop", 
			"OP0", "OP1", "OP2", "OP3", "OpUpdate", "Mdf", "VoidKW", "VarKw", "Info", 
			"CatchKw", "InterfaceKw", "IfKw", "ElseKw", "WhileKw", "ForKw", "InKw", 
			"LoopKw", "Throw", "WhoopsKw", "MethodKw", "DotDotDot", "Slash", "PathSel", 
			"IdUp", "IdLow", "IdChar", "CHAR", "CHARInStringSingle", "CHARInStringMulti", 
			"CharsUrl", "CHARDocText", "AuxBalCurly", "BalCurly", "URL", "ReuseURL", 
			"NativeURL", "Fn", "Fx", "StringMultiOpen", "StringMultiClose", "StringMultiLine", 
			"StringMulti", "StringSingle", "Number", "MUniqueNum", "MHash", "X", 
			"SlashX", "CsP", "ClassSep", "C", "UnderScore", "OR", "ORNS", "Doc", 
			"FS", "FParXs", "FPathSel", "DocText", "BlockComment", "LineComment", 
			"Whitespace"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "'='", "')'", "'['", "']'", "';'", "'<:'", null, 
			null, null, null, null, null, null, "'void'", "'var'", null, "'catch'", 
			"'interface'", "'if'", "'else'", "'while'", "'for'", "'in'", "'loop'", 
			null, "'whoops'", "'method'", "'...'", "'\\'", null, null, null, null, 
			null, null, null, null, null, null, null, "'.'", "'_'", null, "'('"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, "CastOp", "Uop", "OP0", 
			"OP1", "OP2", "OP3", "OpUpdate", "Mdf", "VoidKW", "VarKw", "Info", "CatchKw", 
			"InterfaceKw", "IfKw", "ElseKw", "WhileKw", "ForKw", "InKw", "LoopKw", 
			"Throw", "WhoopsKw", "MethodKw", "DotDotDot", "Slash", "PathSel", "ReuseURL", 
			"NativeURL", "StringMulti", "StringSingle", "Number", "MUniqueNum", "MHash", 
			"X", "SlashX", "CsP", "ClassSep", "UnderScore", "OR", "ORNS", "Doc", 
			"BlockComment", "LineComment", "Whitespace"
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


	public L42Lexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "L42.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\64\u02e9\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\3"+
		"\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\n"+
		"\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u00ae\n\13\3\f"+
		"\3\f\3\f\5\f\u00b3\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\5\r\u00c2\n\r\3\16\3\16\3\16\3\16\5\16\u00c8\n\16\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00e5\n\17\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u010f\n\20"+
		"\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u0127\n\23\3\23\3\23\3\23"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33"+
		"\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\3\34\3\34\5\34\u016a\n\34\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3 \3"+
		" \3!\3!\3!\3\"\7\"\u0184\n\"\f\"\16\"\u0187\13\"\3\"\3\"\3#\7#\u018c\n"+
		"#\f#\16#\u018f\13#\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3"+
		"+\7+\u01a2\n+\f+\16+\u01a5\13+\3+\3+\3+\3+\3+\5+\u01ac\n+\3,\6,\u01af"+
		"\n,\r,\16,\u01b0\3-\3-\3-\3-\3-\3-\3-\7-\u01ba\n-\f-\16-\u01bd\13-\3-"+
		"\3-\3-\3-\3.\3.\3.\3.\3.\3.\3.\3.\7.\u01cb\n.\f.\16.\u01ce\13.\3.\3.\3"+
		".\3.\3/\3/\3/\7/\u01d7\n/\f/\16/\u01da\13/\5/\u01dc\n/\3\60\3\60\7\60"+
		"\u01e0\n\60\f\60\16\60\u01e3\13\60\3\61\3\61\3\61\3\61\3\61\7\61\u01ea"+
		"\n\61\f\61\16\61\u01ed\13\61\3\61\3\61\3\62\7\62\u01f2\n\62\f\62\16\62"+
		"\u01f5\13\62\3\62\3\62\3\62\3\62\3\63\7\63\u01fc\n\63\f\63\16\63\u01ff"+
		"\13\63\3\63\3\63\7\63\u0203\n\63\f\63\16\63\u0206\13\63\3\63\3\63\3\64"+
		"\3\64\6\64\u020c\n\64\r\64\16\64\u020d\3\64\3\64\3\65\3\65\7\65\u0214"+
		"\n\65\f\65\16\65\u0217\13\65\3\65\3\65\3\66\3\66\7\66\u021d\n\66\f\66"+
		"\16\66\u0220\13\66\3\67\3\67\3\67\7\67\u0225\n\67\f\67\16\67\u0228\13"+
		"\67\3\67\3\67\3\67\3\67\3\67\38\38\38\68\u0232\n8\r8\168\u0233\58\u0236"+
		"\n8\38\38\38\78\u023b\n8\f8\168\u023e\138\38\38\38\38\58\u0244\n8\39\3"+
		"9\3:\3:\3:\3:\3:\6:\u024d\n:\r:\16:\u024e\5:\u0251\n:\3:\3:\3:\7:\u0256"+
		"\n:\f:\16:\u0259\13:\5:\u025b\n:\3;\3;\3;\3;\7;\u0261\n;\f;\16;\u0264"+
		"\13;\3<\3<\3=\3=\7=\u026a\n=\f=\16=\u026d\13=\3=\3=\3=\3=\5=\u0273\n="+
		"\3>\3>\3?\3?\3?\3?\3?\3?\5?\u027d\n?\3@\3@\3A\3A\3A\3A\5A\u0285\nA\3A"+
		"\3A\3A\3A\5A\u028b\nA\3B\3B\3B\5B\u0290\nB\3B\3B\3C\3C\3C\3C\3C\3C\7C"+
		"\u029a\nC\fC\16C\u029d\13C\3C\3C\5C\u02a1\nC\3D\3D\3D\5D\u02a6\nD\3D\3"+
		"D\3D\5D\u02ab\nD\3D\3D\3D\3D\5D\u02b1\nD\3D\3D\5D\u02b5\nD\5D\u02b7\n"+
		"D\5D\u02b9\nD\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\5E\u02c7\nE\3F\3F\3"+
		"F\3F\3F\7F\u02ce\nF\fF\16F\u02d1\13F\3F\3F\3F\3F\3F\3G\3G\3G\3G\7G\u02dc"+
		"\nG\fG\16G\u02df\13G\3G\5G\u02e2\nG\3G\3G\3H\3H\3H\3H\4\u02cf\u02dd\2"+
		"I\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20"+
		"\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37"+
		"= ?!A\"C\2E\2G\2I\2K\2M\2O\2Q\2S\2U\2W\2Y#[$]\2_\2a\2c\2e\2g%i&k\'m(o"+
		")q*s+u,w-y\2{.}/\177\60\u0081\61\u0083\2\u0085\2\u0087\2\u0089\2\u008b"+
		"\62\u008d\63\u008f\64\3\2\21\4\2##\u0080\u0080\4\2<<``\5\2,-//\61\61\4"+
		"\2>>@@\4\2&&C\\\7\2&&\62;C\\aac|\4\2\f\f\"\u0080\4\2\"#%\u0080\t\2\"#"+
		"%(*\\^^`|~~\u0080\u0080\7\2\f\f\"AC|~~\u0080\u0080\6\2\f\f\"|~~\u0080"+
		"\u0080\4\2\"\"..\5\2/\60\62;aa\3\3\f\f\5\2\f\f\"\"..\2\u0325\2\3\3\2\2"+
		"\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3"+
		"\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2"+
		"\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2"+
		"\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2"+
		"\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3"+
		"\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2g\3\2\2\2\2i\3\2\2"+
		"\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2"+
		"w\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u008b"+
		"\3\2\2\2\2\u008d\3\2\2\2\2\u008f\3\2\2\2\3\u0091\3\2\2\2\5\u0093\3\2\2"+
		"\2\7\u0095\3\2\2\2\t\u0097\3\2\2\2\13\u0099\3\2\2\2\r\u009b\3\2\2\2\17"+
		"\u009d\3\2\2\2\21\u009f\3\2\2\2\23\u00a2\3\2\2\2\25\u00ad\3\2\2\2\27\u00b2"+
		"\3\2\2\2\31\u00c1\3\2\2\2\33\u00c7\3\2\2\2\35\u00e4\3\2\2\2\37\u010e\3"+
		"\2\2\2!\u0110\3\2\2\2#\u0115\3\2\2\2%\u0126\3\2\2\2\'\u012b\3\2\2\2)\u0131"+
		"\3\2\2\2+\u013b\3\2\2\2-\u013e\3\2\2\2/\u0143\3\2\2\2\61\u0149\3\2\2\2"+
		"\63\u014d\3\2\2\2\65\u0150\3\2\2\2\67\u0169\3\2\2\29\u016b\3\2\2\2;\u0172"+
		"\3\2\2\2=\u0179\3\2\2\2?\u017d\3\2\2\2A\u017f\3\2\2\2C\u0185\3\2\2\2E"+
		"\u018d\3\2\2\2G\u0192\3\2\2\2I\u0194\3\2\2\2K\u0196\3\2\2\2M\u0198\3\2"+
		"\2\2O\u019a\3\2\2\2Q\u019c\3\2\2\2S\u019e\3\2\2\2U\u01a3\3\2\2\2W\u01ae"+
		"\3\2\2\2Y\u01b2\3\2\2\2[\u01c2\3\2\2\2]\u01db\3\2\2\2_\u01dd\3\2\2\2a"+
		"\u01e4\3\2\2\2c\u01f3\3\2\2\2e\u01fd\3\2\2\2g\u0209\3\2\2\2i\u0211\3\2"+
		"\2\2k\u021a\3\2\2\2m\u0221\3\2\2\2o\u0235\3\2\2\2q\u0245\3\2\2\2s\u0247"+
		"\3\2\2\2u\u025c\3\2\2\2w\u0265\3\2\2\2y\u0267\3\2\2\2{\u0274\3\2\2\2}"+
		"\u027c\3\2\2\2\177\u027e\3\2\2\2\u0081\u028a\3\2\2\2\u0083\u028f\3\2\2"+
		"\2\u0085\u02a0\3\2\2\2\u0087\u02b8\3\2\2\2\u0089\u02c6\3\2\2\2\u008b\u02c8"+
		"\3\2\2\2\u008d\u02d7\3\2\2\2\u008f\u02e5\3\2\2\2\u0091\u0092\7}\2\2\u0092"+
		"\4\3\2\2\2\u0093\u0094\7\177\2\2\u0094\6\3\2\2\2\u0095\u0096\7?\2\2\u0096"+
		"\b\3\2\2\2\u0097\u0098\7+\2\2\u0098\n\3\2\2\2\u0099\u009a\7]\2\2\u009a"+
		"\f\3\2\2\2\u009b\u009c\7_\2\2\u009c\16\3\2\2\2\u009d\u009e\7=\2\2\u009e"+
		"\20\3\2\2\2\u009f\u00a0\7>\2\2\u00a0\u00a1\7<\2\2\u00a1\22\3\2\2\2\u00a2"+
		"\u00a3\t\2\2\2\u00a3\24\3\2\2\2\u00a4\u00ae\t\3\2\2\u00a5\u00a6\7>\2\2"+
		"\u00a6\u00ae\7>\2\2\u00a7\u00a8\7-\2\2\u00a8\u00ae\7-\2\2\u00a9\u00aa"+
		"\7/\2\2\u00aa\u00ae\7/\2\2\u00ab\u00ac\7,\2\2\u00ac\u00ae\7,\2\2\u00ad"+
		"\u00a4\3\2\2\2\u00ad\u00a5\3\2\2\2\u00ad\u00a7\3\2\2\2\u00ad\u00a9\3\2"+
		"\2\2\u00ad\u00ab\3\2\2\2\u00ae\26\3\2\2\2\u00af\u00b3\t\4\2\2\u00b0\u00b1"+
		"\7@\2\2\u00b1\u00b3\7@\2\2\u00b2\u00af\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b3"+
		"\30\3\2\2\2\u00b4\u00b5\7/\2\2\u00b5\u00c2\7@\2\2\u00b6\u00b7\7?\2\2\u00b7"+
		"\u00c2\7?\2\2\u00b8\u00c2\t\5\2\2\u00b9\u00ba\7@\2\2\u00ba\u00c2\7?\2"+
		"\2\u00bb\u00bc\7>\2\2\u00bc\u00c2\7?\2\2\u00bd\u00be\7?\2\2\u00be\u00c2"+
		"\7@\2\2\u00bf\u00c0\7#\2\2\u00c0\u00c2\7?\2\2\u00c1\u00b4\3\2\2\2\u00c1"+
		"\u00b6\3\2\2\2\u00c1\u00b8\3\2\2\2\u00c1\u00b9\3\2\2\2\u00c1\u00bb\3\2"+
		"\2\2\u00c1\u00bd\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c2\32\3\2\2\2\u00c3\u00c4"+
		"\7(\2\2\u00c4\u00c8\7(\2\2\u00c5\u00c6\7~\2\2\u00c6\u00c8\7~\2\2\u00c7"+
		"\u00c3\3\2\2\2\u00c7\u00c5\3\2\2\2\u00c8\34\3\2\2\2\u00c9\u00ca\7<\2\2"+
		"\u00ca\u00e5\7?\2\2\u00cb\u00cc\7`\2\2\u00cc\u00e5\7?\2\2\u00cd\u00ce"+
		"\7>\2\2\u00ce\u00cf\7>\2\2\u00cf\u00e5\7?\2\2\u00d0\u00d1\7-\2\2\u00d1"+
		"\u00e5\7?\2\2\u00d2\u00d3\7/\2\2\u00d3\u00e5\7?\2\2\u00d4\u00d5\7,\2\2"+
		"\u00d5\u00e5\7?\2\2\u00d6\u00d7\7\61\2\2\u00d7\u00e5\7?\2\2\u00d8\u00d9"+
		"\7-\2\2\u00d9\u00da\7-\2\2\u00da\u00e5\7?\2\2\u00db\u00dc\7/\2\2\u00dc"+
		"\u00dd\7/\2\2\u00dd\u00e5\7?\2\2\u00de\u00df\7,\2\2\u00df\u00e0\7,\2\2"+
		"\u00e0\u00e5\7?\2\2\u00e1\u00e2\7@\2\2\u00e2\u00e3\7@\2\2\u00e3\u00e5"+
		"\7?\2\2\u00e4\u00c9\3\2\2\2\u00e4\u00cb\3\2\2\2\u00e4\u00cd\3\2\2\2\u00e4"+
		"\u00d0\3\2\2\2\u00e4\u00d2\3\2\2\2\u00e4\u00d4\3\2\2\2\u00e4\u00d6\3\2"+
		"\2\2\u00e4\u00d8\3\2\2\2\u00e4\u00db\3\2\2\2\u00e4\u00de\3\2\2\2\u00e4"+
		"\u00e1\3\2\2\2\u00e5\36\3\2\2\2\u00e6\u00e7\7h\2\2\u00e7\u00e8\7y\2\2"+
		"\u00e8\u00e9\7f\2\2\u00e9\u00ea\7\"\2\2\u00ea\u00eb\7o\2\2\u00eb\u00ec"+
		"\7w\2\2\u00ec\u010f\7v\2\2\u00ed\u00ee\7h\2\2\u00ee\u00ef\7y\2\2\u00ef"+
		"\u00f0\7f\2\2\u00f0\u00f1\7\"\2\2\u00f1\u00f2\7k\2\2\u00f2\u00f3\7o\2"+
		"\2\u00f3\u010f\7o\2\2\u00f4\u00f5\7k\2\2\u00f5\u00f6\7o\2\2\u00f6\u010f"+
		"\7o\2\2\u00f7\u00f8\7o\2\2\u00f8\u00f9\7w\2\2\u00f9\u010f\7v\2\2\u00fa"+
		"\u00fb\7n\2\2\u00fb\u00fc\7g\2\2\u00fc\u00fd\7p\2\2\u00fd\u010f\7v\2\2"+
		"\u00fe\u00ff\7t\2\2\u00ff\u0100\7g\2\2\u0100\u0101\7c\2\2\u0101\u010f"+
		"\7f\2\2\u0102\u0103\7e\2\2\u0103\u0104\7c\2\2\u0104\u0105\7r\2\2\u0105"+
		"\u0106\7u\2\2\u0106\u0107\7w\2\2\u0107\u0108\7n\2\2\u0108\u010f\7g\2\2"+
		"\u0109\u010a\7e\2\2\u010a\u010b\7n\2\2\u010b\u010c\7c\2\2\u010c\u010d"+
		"\7u\2\2\u010d\u010f\7u\2\2\u010e\u00e6\3\2\2\2\u010e\u00ed\3\2\2\2\u010e"+
		"\u00f4\3\2\2\2\u010e\u00f7\3\2\2\2\u010e\u00fa\3\2\2\2\u010e\u00fe\3\2"+
		"\2\2\u010e\u0102\3\2\2\2\u010e\u0109\3\2\2\2\u010f \3\2\2\2\u0110\u0111"+
		"\7x\2\2\u0111\u0112\7q\2\2\u0112\u0113\7k\2\2\u0113\u0114\7f\2\2\u0114"+
		"\"\3\2\2\2\u0115\u0116\7x\2\2\u0116\u0117\7c\2\2\u0117\u0118\7t\2\2\u0118"+
		"$\3\2\2\2\u0119\u011a\7%\2\2\u011a\u011b\7p\2\2\u011b\u011c\7q\2\2\u011c"+
		"\u011d\7t\2\2\u011d\u011e\7o\2\2\u011e\u0127\7}\2\2\u011f\u0120\7%\2\2"+
		"\u0120\u0121\7v\2\2\u0121\u0122\7{\2\2\u0122\u0123\7r\2\2\u0123\u0124"+
		"\7g\2\2\u0124\u0125\7f\2\2\u0125\u0127\7}\2\2\u0126\u0119\3\2\2\2\u0126"+
		"\u011f\3\2\2\2\u0127\u0128\3\2\2\2\u0128\u0129\5U+\2\u0129\u012a\7\177"+
		"\2\2\u012a&\3\2\2\2\u012b\u012c\7e\2\2\u012c\u012d\7c\2\2\u012d\u012e"+
		"\7v\2\2\u012e\u012f\7e\2\2\u012f\u0130\7j\2\2\u0130(\3\2\2\2\u0131\u0132"+
		"\7k\2\2\u0132\u0133\7p\2\2\u0133\u0134\7v\2\2\u0134\u0135\7g\2\2\u0135"+
		"\u0136\7t\2\2\u0136\u0137\7h\2\2\u0137\u0138\7c\2\2\u0138\u0139\7e\2\2"+
		"\u0139\u013a\7g\2\2\u013a*\3\2\2\2\u013b\u013c\7k\2\2\u013c\u013d\7h\2"+
		"\2\u013d,\3\2\2\2\u013e\u013f\7g\2\2\u013f\u0140\7n\2\2\u0140\u0141\7"+
		"u\2\2\u0141\u0142\7g\2\2\u0142.\3\2\2\2\u0143\u0144\7y\2\2\u0144\u0145"+
		"\7j\2\2\u0145\u0146\7k\2\2\u0146\u0147\7n\2\2\u0147\u0148\7g\2\2\u0148"+
		"\60\3\2\2\2\u0149\u014a\7h\2\2\u014a\u014b\7q\2\2\u014b\u014c\7t\2\2\u014c"+
		"\62\3\2\2\2\u014d\u014e\7k\2\2\u014e\u014f\7p\2\2\u014f\64\3\2\2\2\u0150"+
		"\u0151\7n\2\2\u0151\u0152\7q\2\2\u0152\u0153\7q\2\2\u0153\u0154\7r\2\2"+
		"\u0154\66\3\2\2\2\u0155\u0156\7t\2\2\u0156\u0157\7g\2\2\u0157\u0158\7"+
		"v\2\2\u0158\u0159\7w\2\2\u0159\u015a\7t\2\2\u015a\u016a\7p\2\2\u015b\u015c"+
		"\7g\2\2\u015c\u015d\7t\2\2\u015d\u015e\7t\2\2\u015e\u015f\7q\2\2\u015f"+
		"\u016a\7t\2\2\u0160\u0161\7g\2\2\u0161\u0162\7z\2\2\u0162\u0163\7e\2\2"+
		"\u0163\u0164\7g\2\2\u0164\u0165\7r\2\2\u0165\u0166\7v\2\2\u0166\u0167"+
		"\7k\2\2\u0167\u0168\7q\2\2\u0168\u016a\7p\2\2\u0169\u0155\3\2\2\2\u0169"+
		"\u015b\3\2\2\2\u0169\u0160\3\2\2\2\u016a8\3\2\2\2\u016b\u016c\7y\2\2\u016c"+
		"\u016d\7j\2\2\u016d\u016e\7q\2\2\u016e\u016f\7q\2\2\u016f\u0170\7r\2\2"+
		"\u0170\u0171\7u\2\2\u0171:\3\2\2\2\u0172\u0173\7o\2\2\u0173\u0174\7g\2"+
		"\2\u0174\u0175\7v\2\2\u0175\u0176\7j\2\2\u0176\u0177\7q\2\2\u0177\u0178"+
		"\7f\2\2\u0178<\3\2\2\2\u0179\u017a\7\60\2\2\u017a\u017b\7\60\2\2\u017b"+
		"\u017c\7\60\2\2\u017c>\3\2\2\2\u017d\u017e\7^\2\2\u017e@\3\2\2\2\u017f"+
		"\u0180\7)\2\2\u0180\u0181\5\u0087D\2\u0181B\3\2\2\2\u0182\u0184\7a\2\2"+
		"\u0183\u0182\3\2\2\2\u0184\u0187\3\2\2\2\u0185\u0183\3\2\2\2\u0185\u0186"+
		"\3\2\2\2\u0186\u0188\3\2\2\2\u0187\u0185\3\2\2\2\u0188\u0189\t\6\2\2\u0189"+
		"D\3\2\2\2\u018a\u018c\7a\2\2\u018b\u018a\3\2\2\2\u018c\u018f\3\2\2\2\u018d"+
		"\u018b\3\2\2\2\u018d\u018e\3\2\2\2\u018e\u0190\3\2\2\2\u018f\u018d\3\2"+
		"\2\2\u0190\u0191\4c|\2\u0191F\3\2\2\2\u0192\u0193\t\7\2\2\u0193H\3\2\2"+
		"\2\u0194\u0195\t\b\2\2\u0195J\3\2\2\2\u0196\u0197\t\t\2\2\u0197L\3\2\2"+
		"\2\u0198\u0199\4\"\u0080\2\u0199N\3\2\2\2\u019a\u019b\t\n\2\2\u019bP\3"+
		"\2\2\2\u019c\u019d\t\13\2\2\u019dR\3\2\2\2\u019e\u019f\t\f\2\2\u019fT"+
		"\3\2\2\2\u01a0\u01a2\5S*\2\u01a1\u01a0\3\2\2\2\u01a2\u01a5\3\2\2\2\u01a3"+
		"\u01a1\3\2\2\2\u01a3\u01a4\3\2\2\2\u01a4\u01ab\3\2\2\2\u01a5\u01a3\3\2"+
		"\2\2\u01a6\u01a7\7}\2\2\u01a7\u01a8\5U+\2\u01a8\u01a9\7\177\2\2\u01a9"+
		"\u01aa\5U+\2\u01aa\u01ac\3\2\2\2\u01ab\u01a6\3\2\2\2\u01ab\u01ac\3\2\2"+
		"\2\u01acV\3\2\2\2\u01ad\u01af\5O(\2\u01ae\u01ad\3\2\2\2\u01af\u01b0\3"+
		"\2\2\2\u01b0\u01ae\3\2\2\2\u01b0\u01b1\3\2\2\2\u01b1X\3\2\2\2\u01b2\u01b3"+
		"\7t\2\2\u01b3\u01b4\7g\2\2\u01b4\u01b5\7w\2\2\u01b5\u01b6\7u\2\2\u01b6"+
		"\u01b7\7g\2\2\u01b7\u01bb\3\2\2\2\u01b8\u01ba\5\u008fH\2\u01b9\u01b8\3"+
		"\2\2\2\u01ba\u01bd\3\2\2\2\u01bb\u01b9\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc"+
		"\u01be\3\2\2\2\u01bd\u01bb\3\2\2\2\u01be\u01bf\7]\2\2\u01bf\u01c0\5W,"+
		"\2\u01c0\u01c1\7_\2\2\u01c1Z\3\2\2\2\u01c2\u01c3\7p\2\2\u01c3\u01c4\7"+
		"c\2\2\u01c4\u01c5\7v\2\2\u01c5\u01c6\7k\2\2\u01c6\u01c7\7x\2\2\u01c7\u01c8"+
		"\7g\2\2\u01c8\u01cc\3\2\2\2\u01c9\u01cb\5\u008fH\2\u01ca\u01c9\3\2\2\2"+
		"\u01cb\u01ce\3\2\2\2\u01cc\u01ca\3\2\2\2\u01cc\u01cd\3\2\2\2\u01cd\u01cf"+
		"\3\2\2\2\u01ce\u01cc\3\2\2\2\u01cf\u01d0\7}\2\2\u01d0\u01d1\5U+\2\u01d1"+
		"\u01d2\7\177\2\2\u01d2\\\3\2\2\2\u01d3\u01dc\7\62\2\2\u01d4\u01d8\4\63"+
		";\2\u01d5\u01d7\4\62;\2\u01d6\u01d5\3\2\2\2\u01d7\u01da\3\2\2\2\u01d8"+
		"\u01d6\3\2\2\2\u01d8\u01d9\3\2\2\2\u01d9\u01dc\3\2\2\2\u01da\u01d8\3\2"+
		"\2\2\u01db\u01d3\3\2\2\2\u01db\u01d4\3\2\2\2\u01dc^\3\2\2\2\u01dd\u01e1"+
		"\5E#\2\u01de\u01e0\5G$\2\u01df\u01de\3\2\2\2\u01e0\u01e3\3\2\2\2\u01e1"+
		"\u01df\3\2\2\2\u01e1\u01e2\3\2\2\2\u01e2`\3\2\2\2\u01e3\u01e1\3\2\2\2"+
		"\u01e4\u01e5\7$\2\2\u01e5\u01e6\7$\2\2\u01e6\u01e7\7$\2\2\u01e7\u01eb"+
		"\3\2\2\2\u01e8\u01ea\7\'\2\2\u01e9\u01e8\3\2\2\2\u01ea\u01ed\3\2\2\2\u01eb"+
		"\u01e9\3\2\2\2\u01eb\u01ec\3\2\2\2\u01ec\u01ee\3\2\2\2\u01ed\u01eb\3\2"+
		"\2\2\u01ee\u01ef\7\f\2\2\u01efb\3\2\2\2\u01f0\u01f2\t\r\2\2\u01f1\u01f0"+
		"\3\2\2\2\u01f2\u01f5\3\2\2\2\u01f3\u01f1\3\2\2\2\u01f3\u01f4\3\2\2\2\u01f4"+
		"\u01f6\3\2\2\2\u01f5\u01f3\3\2\2\2\u01f6\u01f7\7$\2\2\u01f7\u01f8\7$\2"+
		"\2\u01f8\u01f9\7$\2\2\u01f9d\3\2\2\2\u01fa\u01fc\t\r\2\2\u01fb\u01fa\3"+
		"\2\2\2\u01fc\u01ff\3\2\2\2\u01fd\u01fb\3\2\2\2\u01fd\u01fe\3\2\2\2\u01fe"+
		"\u0200\3\2\2\2\u01ff\u01fd\3\2\2\2\u0200\u0204\7~\2\2\u0201\u0203\5M\'"+
		"\2\u0202\u0201\3\2\2\2\u0203\u0206\3\2\2\2\u0204\u0202\3\2\2\2\u0204\u0205"+
		"\3\2\2\2\u0205\u0207\3\2\2\2\u0206\u0204\3\2\2\2\u0207\u0208\7\f\2\2\u0208"+
		"f\3\2\2\2\u0209\u020b\5a\61\2\u020a\u020c\5e\63\2\u020b\u020a\3\2\2\2"+
		"\u020c\u020d\3\2\2\2\u020d\u020b\3\2\2\2\u020d\u020e\3\2\2\2\u020e\u020f"+
		"\3\2\2\2\u020f\u0210\5c\62\2\u0210h\3\2\2\2\u0211\u0215\7$\2\2\u0212\u0214"+
		"\5K&\2\u0213\u0212\3\2\2\2\u0214\u0217\3\2\2\2\u0215\u0213\3\2\2\2\u0215"+
		"\u0216\3\2\2\2\u0216\u0218\3\2\2\2\u0217\u0215\3\2\2\2\u0218\u0219\7$"+
		"\2\2\u0219j\3\2\2\2\u021a\u021e\4\62;\2\u021b\u021d\t\16\2\2\u021c\u021b"+
		"\3\2\2\2\u021d\u0220\3\2\2\2\u021e\u021c\3\2\2\2\u021e\u021f\3\2\2\2\u021f"+
		"l\3\2\2\2\u0220\u021e\3\2\2\2\u0221\u0226\5_\60\2\u0222\u0223\7%\2\2\u0223"+
		"\u0225\5_\60\2\u0224\u0222\3\2\2\2\u0225\u0228\3\2\2\2\u0226\u0224\3\2"+
		"\2\2\u0226\u0227\3\2\2\2\u0227\u0229\3\2\2\2\u0228\u0226\3\2\2\2\u0229"+
		"\u022a\7<\2\2\u022a\u022b\7<\2\2\u022b\u022c\3\2\2\2\u022c\u022d\5]/\2"+
		"\u022dn\3\2\2\2\u022e\u022f\7%\2\2\u022f\u0236\7&\2\2\u0230\u0232\7%\2"+
		"\2\u0231\u0230\3\2\2\2\u0232\u0233\3\2\2\2\u0233\u0231\3\2\2\2\u0233\u0234"+
		"\3\2\2\2\u0234\u0236\3\2\2\2\u0235\u022e\3\2\2\2\u0235\u0231\3\2\2\2\u0236"+
		"\u0237\3\2\2\2\u0237\u023c\5_\60\2\u0238\u0239\7%\2\2\u0239\u023b\5_\60"+
		"\2\u023a\u0238\3\2\2\2\u023b\u023e\3\2\2\2\u023c\u023a\3\2\2\2\u023c\u023d"+
		"\3\2\2\2\u023d\u0243\3\2\2\2\u023e\u023c\3\2\2\2\u023f\u0240\7<\2\2\u0240"+
		"\u0241\7<\2\2\u0241\u0242\3\2\2\2\u0242\u0244\5]/\2\u0243\u023f\3\2\2"+
		"\2\u0243\u0244\3\2\2\2\u0244p\3\2\2\2\u0245\u0246\5_\60\2\u0246r\3\2\2"+
		"\2\u0247\u025a\7^\2\2\u0248\u025b\5_\60\2\u0249\u024a\7%\2\2\u024a\u0251"+
		"\7&\2\2\u024b\u024d\7%\2\2\u024c\u024b\3\2\2\2\u024d\u024e\3\2\2\2\u024e"+
		"\u024c\3\2\2\2\u024e\u024f\3\2\2\2\u024f\u0251\3\2\2\2\u0250\u0249\3\2"+
		"\2\2\u0250\u024c\3\2\2\2\u0251\u0252\3\2\2\2\u0252\u0257\5_\60\2\u0253"+
		"\u0254\7%\2\2\u0254\u0256\5_\60\2\u0255\u0253\3\2\2\2\u0256\u0259\3\2"+
		"\2\2\u0257\u0255\3\2\2\2\u0257\u0258\3\2\2\2\u0258\u025b\3\2\2\2\u0259"+
		"\u0257\3\2\2\2\u025a\u0248\3\2\2\2\u025a\u0250\3\2\2\2\u025bt\3\2\2\2"+
		"\u025c\u0262\5y=\2\u025d\u025e\5w<\2\u025e\u025f\5y=\2\u025f\u0261\3\2"+
		"\2\2\u0260\u025d\3\2\2\2\u0261\u0264\3\2\2\2\u0262\u0260\3\2\2\2\u0262"+
		"\u0263\3\2\2\2\u0263v\3\2\2\2\u0264\u0262\3\2\2\2\u0265\u0266\7\60\2\2"+
		"\u0266x\3\2\2\2\u0267\u026b\5C\"\2\u0268\u026a\5G$\2\u0269\u0268\3\2\2"+
		"\2\u026a\u026d\3\2\2\2\u026b\u0269\3\2\2\2\u026b\u026c\3\2\2\2\u026c\u0272"+
		"\3\2\2\2\u026d\u026b\3\2\2\2\u026e\u026f\7<\2\2\u026f\u0270\7<\2\2\u0270"+
		"\u0271\3\2\2\2\u0271\u0273\5]/\2\u0272\u026e\3\2\2\2\u0272\u0273\3\2\2"+
		"\2\u0273z\3\2\2\2\u0274\u0275\7a\2\2\u0275|\3\2\2\2\u0276\u0277\7\"\2"+
		"\2\u0277\u027d\7*\2\2\u0278\u0279\7.\2\2\u0279\u027d\7*\2\2\u027a\u027b"+
		"\7\f\2\2\u027b\u027d\7*\2\2\u027c\u0276\3\2\2\2\u027c\u0278\3\2\2\2\u027c"+
		"\u027a\3\2\2\2\u027d~\3\2\2\2\u027e\u027f\7*\2\2\u027f\u0080\3\2\2\2\u0280"+
		"\u0281\7B\2\2\u0281\u028b\5\u0087D\2\u0282\u0284\7B\2\2\u0283\u0285\5"+
		"\u0087D\2\u0284\u0283\3\2\2\2\u0284\u0285\3\2\2\2\u0285\u0286\3\2\2\2"+
		"\u0286\u0287\7}\2\2\u0287\u0288\5\u0089E\2\u0288\u0289\7\177\2\2\u0289"+
		"\u028b\3\2\2\2\u028a\u0280\3\2\2\2\u028a\u0282\3\2\2\2\u028b\u0082\3\2"+
		"\2\2\u028c\u0290\5m\67\2\u028d\u0290\5o8\2\u028e\u0290\5q9\2\u028f\u028c"+
		"\3\2\2\2\u028f\u028d\3\2\2\2\u028f\u028e\3\2\2\2\u0290\u0291\3\2\2\2\u0291"+
		"\u0292\5\u0085C\2\u0292\u0084\3\2\2\2\u0293\u0294\7*\2\2\u0294\u02a1\7"+
		"+\2\2\u0295\u0296\7*\2\2\u0296\u029b\5q9\2\u0297\u0298\t\r\2\2\u0298\u029a"+
		"\5q9\2\u0299\u0297\3\2\2\2\u029a\u029d\3\2\2\2\u029b\u0299\3\2\2\2\u029b"+
		"\u029c\3\2\2\2\u029c\u029e\3\2\2\2\u029d\u029b\3\2\2\2\u029e\u029f\7+"+
		"\2\2\u029f\u02a1\3\2\2\2\u02a0\u0293\3\2\2\2\u02a0\u0295\3\2\2\2\u02a1"+
		"\u0086\3\2\2\2\u02a2\u02a5\5\u0083B\2\u02a3\u02a4\7\60\2\2\u02a4\u02a6"+
		"\5q9\2\u02a5\u02a3\3\2\2\2\u02a5\u02a6\3\2\2\2\u02a6\u02b9\3\2\2\2\u02a7"+
		"\u02aa\5\u0085C\2\u02a8\u02a9\7\60\2\2\u02a9\u02ab\5q9\2\u02aa\u02a8\3"+
		"\2\2\2\u02aa\u02ab\3\2\2\2\u02ab\u02b9\3\2\2\2\u02ac\u02b6\5u;\2\u02ad"+
		"\u02ae\7\60\2\2\u02ae\u02b1\5\u0083B\2\u02af\u02b1\5\u0085C\2\u02b0\u02ad"+
		"\3\2\2\2\u02b0\u02af\3\2\2\2\u02b1\u02b4\3\2\2\2\u02b2\u02b3\7\60\2\2"+
		"\u02b3\u02b5\5q9\2\u02b4\u02b2\3\2\2\2\u02b4\u02b5\3\2\2\2\u02b5\u02b7"+
		"\3\2\2\2\u02b6\u02b0\3\2\2\2\u02b6\u02b7\3\2\2\2\u02b7\u02b9\3\2\2\2\u02b8"+
		"\u02a2\3\2\2\2\u02b8\u02a7\3\2\2\2\u02b8\u02ac\3\2\2\2\u02b9\u0088\3\2"+
		"\2\2\u02ba\u02c7\3\2\2\2\u02bb\u02bc\5Q)\2\u02bc\u02bd\5\u0089E\2\u02bd"+
		"\u02c7\3\2\2\2\u02be\u02bf\5\u0081A\2\u02bf\u02c0\5\u0089E\2\u02c0\u02c7"+
		"\3\2\2\2\u02c1\u02c2\7}\2\2\u02c2\u02c3\5\u0089E\2\u02c3\u02c4\7\177\2"+
		"\2\u02c4\u02c5\5\u0089E\2\u02c5\u02c7\3\2\2\2\u02c6\u02ba\3\2\2\2\u02c6"+
		"\u02bb\3\2\2\2\u02c6\u02be\3\2\2\2\u02c6\u02c1\3\2\2\2\u02c7\u008a\3\2"+
		"\2\2\u02c8\u02c9\7\61\2\2\u02c9\u02ca\7,\2\2\u02ca\u02cf\3\2\2\2\u02cb"+
		"\u02ce\5\u008bF\2\u02cc\u02ce\13\2\2\2\u02cd\u02cb\3\2\2\2\u02cd\u02cc"+
		"\3\2\2\2\u02ce\u02d1\3\2\2\2\u02cf\u02d0\3\2\2\2\u02cf\u02cd\3\2\2\2\u02d0"+
		"\u02d2\3\2\2\2\u02d1\u02cf\3\2\2\2\u02d2\u02d3\7,\2\2\u02d3\u02d4\7\61"+
		"\2\2\u02d4\u02d5\3\2\2\2\u02d5\u02d6\bF\2\2\u02d6\u008c\3\2\2\2\u02d7"+
		"\u02d8\7\61\2\2\u02d8\u02d9\7\61\2\2\u02d9\u02dd\3\2\2\2\u02da\u02dc\13"+
		"\2\2\2\u02db\u02da\3\2\2\2\u02dc\u02df\3\2\2\2\u02dd\u02de\3\2\2\2\u02dd"+
		"\u02db\3\2\2\2\u02de\u02e1\3\2\2\2\u02df\u02dd\3\2\2\2\u02e0\u02e2\t\17"+
		"\2\2\u02e1\u02e0\3\2\2\2\u02e2\u02e3\3\2\2\2\u02e3\u02e4\bG\2\2\u02e4"+
		"\u008e\3\2\2\2\u02e5\u02e6\t\20\2\2\u02e6\u02e7\3\2\2\2\u02e7\u02e8\b"+
		"H\2\2\u02e8\u0090\3\2\2\29\2\u00ad\u00b2\u00c1\u00c7\u00e4\u010e\u0126"+
		"\u0169\u0185\u018d\u01a3\u01ab\u01b0\u01bb\u01cc\u01d8\u01db\u01e1\u01eb"+
		"\u01f3\u01fd\u0204\u020d\u0215\u021e\u0226\u0233\u0235\u023c\u0243\u024e"+
		"\u0250\u0257\u025a\u0262\u026b\u0272\u027c\u0284\u028a\u028f\u029b\u02a0"+
		"\u02a5\u02aa\u02b0\u02b4\u02b6\u02b8\u02c6\u02cd\u02cf\u02dd\u02e1\3\2"+
		"\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}