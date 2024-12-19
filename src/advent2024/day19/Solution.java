package src.advent2024.day19;

import src.PuzzleSolver;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Solution extends PuzzleSolver {

    public static void main(String[] args) {
        new Solution().run();
    }

    @Override
    public List<String> getExampleInput1() {
        return List.of(
                "r, wr, b, g, bwu, rb, gb, br\n" +
                        "\n" +
                        "brwrr\n" +
                        "bggr\n" +
                        "gbbr\n" +
                        "rrbgbr\n" +
                        "ubwu\n" +
                        "bwurrg\n" +
                        "brgr\n" +
                        "bbrgwb",
                "brgugg, bgurr, wubb, bbg, uuuubwu, wuu, urwg, bbrgu, bgwu, brbg, wgw, wwbbb, rur, urbgw, rwgrr, wwbbr, uww, wwg, wub, uwugw, bu, uwwug, uuwrb, burwbb, wrburb, rrwugw, uwbb, ggugr, bwg, wwubu, bug, urrrgwg, grgwwu, bbuw, urg, bgrbru, urr, rwggwb, wbu, gbru, ggurrbr, ugug, rbbgbr, gwruu, bgr, rub, wgb, ugu, uwwwbg, ubbugbwg, gubbbwg, bgbrwu, uugr, guwrub, rrrubw, wwr, gu, wru, bgrg, r, uwwb, ubu, www, rgur, bur, bgbgwwu, gubg, rrrr, u, guwrg, gbwu, bugrbb, wuru, ub, bgggg, bbwu, rbrw, uuw, rbubb, bbwg, urugrg, urwurrw, gru, grg, gw, gwwrwbg, urrbrr, uguw, rrgw, uubbug, buu, rggubg, gwwb, ru, wbwgw, rbrruwbg, ubw, wgu, bwbbwgg, wrwugb, buru, wgwrr, rbbggr, rg, bgurwg, gwbb, rurbbugu, gbuw, bgb, wgrb, rgr, uggugwr, bbb, rgw, wurwww, wwwbbwr, rgguuwr, rbg, bguuuuru, brr, brwg, bwr, rggrugu, uwbwbg, bgur, rb, grr, uur, ggr, rruru, gbgu, gugbg, wrrwuggr, ug, ugr, uw, rgrwr, urwrwgwb, guugg, rrr, bwbwgu, uwubr, grbb, gurw, ubgb, bbwbbr, wgwwwgu, rrwr, wbub, brrg, gwwrb, guggrbu, wgrbw, grrubbg, bwwrb, wggrr, uwr, wuwwugrw, rrwgbug, bugw, bgbubwg, ubggbbug, rww, ubrr, wruub, wwgb, gub, bgwwgr, wrugw, brug, wbgb, uguurbw, wurw, gbg, uubwrg, bub, buurg, wurrwb, bwrrw, guub, ruruu, wgwr, ggb, rbrr, bww, rgguur, wubw, ugrb, rwr, uuuwb, rgwww, urw, rbwbu, bwb, buurub, ww, urrrb, wgwwg, ugwuguw, wurg, ugg, g, rbgg, wrw, ruww, wbguggb, rbr, gggww, bwrrrw, gg, rbbrg, bguw, rgrrgr, ugb, uwgu, urguw, grwb, brgg, gbugb, ubwbrrrg, uwu, rgug, gbb, gwg, uru, wwu, ubwgw, wwuwr, ubgug, wuw, uruub, wrwwwru, rrgbubw, wubg, wgwgw, ubg, wbwbu, rrggr, uwgw, wrbb, gwwrbwub, rwu, ubur, bubwu, gur, wug, gbbwbbuw, gwuubwu, gbrbgr, urrgwww, uuururwu, uwru, wgwgwg, rrb, gbu, brwu, uwrbr, wgrg, rrrg, wgbgwrw, guwb, gurr, rbb, wgugw, wur, uub, rrgrg, bgwrgg, bbu, ggwbguu, ugubu, rrbg, rwb, wbwr, grwrr, gggrrbrg, wwwbwrg, wguuru, bru, wwrb, wbb, ubgu, bubu, rru, bubgur, gbr, bubur, bb, guwgugwu, bgu, uug, buw, wrg, ububb, ggu, guuwu, wruuw, wuuw, rgb, bw, bgg, rrg, wwb, gbwbg, brb, uwwwb, uurr, ugbg, gggbwu, wrugb, buwu, gwww, ruwu, wgrrr, guwbrw, gbrb, brru, rbu, gr, uubbrg, buwggbrr, rbuw, ur, wrb, wguurbg, w, gbbrbr, grrr, ggugww, gug, wgg, uggbugu, wuwgr, rrw, uruw, wg, bwgbu, ggw, grgbrwr, rugrbgu, grw, rrwurb, rwrugrr, bgubg, uu, gwbwrb, wwgugg, wrr, wggu, gbw, rwgurww, gwgguurr, gwbwg, bwrggw, ggg, bwu, ubbur, guguu, wbg, wggrw, guu, wugbbb, rr, guur, wu, urbrbrwb, wbr, gwgugbw, ubr, uugrr, rbw, bbgbw, bggruw, ruuwwu, gurgu, bggw, grb, wgbu, bbw, guw, grwub, gggw, bbbuwuw, rgbgubrr, uburwub, bbgwb, rgubgwbw, brw, gugbbr, bbur, gurrw, uggb, wwgbw, bg, bgwbwuw, ugwg, uwwr, rwg, ugw, rrrb, rbuwrrwr, rwguubb, rwgugu, gbrgbg, ugbbr, ugru, ruw, bwbbb, wbw, ubb, rbbbgbr, bbrurr, rbug, rgu, rwub, rrgrb, rw, wwgg, ugwbw, gwu, rgwuu, gwb, bgrruw, wrgrwb, wgrr, gwr, rrurru, urb, gwrw, rgg, bwug, bbgrrw, rgbrwu, ruu, wr, gww, wrwwrb, rggbuwb, wb, rbwubb, rbub, bgw, grur, bwrb, uuu, wggbgb, wgr\n" +
                        "\n" +
                        "brgrgruwgwurbwwwwwrrgbuwgwuwguwwrwgbrruwrbwbwwrbgrugrwbgu\n" +
                        "rugbrguubrwrrruguubrrrbugwgguurrrbwbgubugruwgrg\n" +
                        "rwbugwbbbbrwbgrburugrbrgggrrwbrbgbbwubbwwgwrwrbrwuggwwgr\n" +
                        "gburgbrgruugurrwrwrwbuggwgbwgggububbwgggbwggwgbwuubwwubug\n" +
                        "brgwburwrurrgrwwrubwrrggwuugrwbbbugbbuurwrbggrwburggurubwgr\n" +
                        "wrrrbwgbwbrrwrrbwwgwbwrrrgururuuwrrrwgrrwgbrgwbgrr\n" +
                        "bwbrgruugrrbuurwgubbrgbggrrgrgwuwuguwwwbrwgwwwwrubgrgu\n" +
                        "rgbgubgguurgbwubwwwbwgbbguruwwuwuwrrbwwurbrrubgwb\n" +
                        "wugbrwgbgrwuwgwwugwbwrubwugrwbwubwwbbrburwurburgr\n" +
                        "uguubbwwgugwugwwggwbbwgwuwggrrrurbggrwbgbrbwbrubgrbrru\n" +
                        "buuguwwbbbwgurbgwbwurbgubwubgrwugbrwgrugurrbbuwurgrb\n" +
                        "rbuurbugbgwuwrugurbrwbgwbbggruwuwwwwgrgrgurrrwwbgu\n" +
                        "ggugwwgurwwbbugbgbubbgbrbrwwwbrbwrubugbbwgbbrgugu\n" +
                        "brgrurwbrbguuubbgggbugrgurrwrbbwgwwuwrbgbruugggwwubgruubgwg\n" +
                        "brgrubbgggugurwuwbrurbrbrbrgubbrwuubrgrbub\n" +
                        "brgbrbwbrbwbggwwwuwwrrwgrgwubuwugwugwrggwg\n" +
                        "uggbbrwwuwrbbwubwggurbbrgwwwwbrrbbbugrgurrurbggrgwbgggb\n" +
                        "rguwgbgurrwubuguurbgrubbubuuuuwwbwbruwbbgrgrwbrrgg\n" +
                        "brguugbwgwrbuwggrrbgrugurbrwwbwurgrguuwbrwubgw\n" +
                        "brwrwugwgruuubwrgggrbwrgrgwubwggwbrwwrubgwuwuruwu\n" +
                        "grbgwuugrwbgrrubwgwrwbwwuwggwrubrggurruuwubugbww\n" +
                        "wruruggurwuugrgbwbgwggbrbbugwggbbrwuuggbbbrbrbuburwuubuur\n" +
                        "bgrwrwbrgugugbggrbgwgwwrwgbwguwbwugugbgbwgwrwgrw\n" +
                        "wbrrrurrwuuuugbrwuubrwuuwuuwwwwrurbruwrwguuwbgwwbwgu\n" +
                        "bgwrwurgwwuwruuuwbuugwggrbbugbgguuwubugrgrgbbw\n" +
                        "uwurrurugrwbwbrbbgbrwbgugrrubwrwwwgwrruwgwbrurbggwub\n" +
                        "wrbruwuwrbubrwuwwrrrbgrwbwrguwwrugbuugbuurwburwwbr\n" +
                        "brgwwwgwbrwbbrwgugwwgrwgwuubwurugwububuru\n" +
                        "uurwbrgbuwrwwwrgrbbwwrugwwwguwwurbuwrgrubwrb\n" +
                        "bburgwggwbguwbuubgugrbugwbwrrbugwwuguwburuuubuugrbggww\n" +
                        "brgubggbbuguuugbggubwrbbbbrrwggwwuwbwrbbgbwrr\n" +
                        "brgwgwrwrwuuugbwrwubgbubbgrugguurrgrbrbugggrub\n" +
                        "gbugbwrbguubrbrwrgwwwrwuugwwurbwuubwrugrbggurwggbwuwur\n" +
                        "bgwbrbwgggbuurwuwwbgrrwurwurburwrwwrbgrwwrrubbbwwwgwgg\n" +
                        "wrwwurbbbuuggggwrwugbgrgbgbbrrburuugbwgwrbbbgrgr\n" +
                        "ugugwruugwggbwgwuurbrburbrwbububrwubrububwgugwrwurbwbwbb\n" +
                        "brgrggruwuwwgbgrgbgrurgbruugruggrrurrgbwuwuuwgrr\n" +
                        "bggwwrbrbbbuuubuwwrgbbgbrrwuuubwbgugbuugrgrrr\n" +
                        "brgwwrgwgrguwwbuugruwubwbbwwuwgbugwuuurwggrwguuuu\n" +
                        "ggbgruuuruubuwgwuggggrgrugubwggbbrwubrbrbugrrg\n" +
                        "rbugbuwububgrwuwrrwwgggrwrwgwgrubgubruuurggggbrgw\n" +
                        "bubgbgrwgruwburwbbrurbgbbggrurbbugurbrgubgrrgwugbwbb\n" +
                        "gwuwrugwbrubuwrrbbubuuwgrbgwrggguuruugruwbgwrurgrbbbgbbubr\n" +
                        "gbwbgbgugbwgbwrwbbwbwbrggrugwbgrbubwuwbgwbwgw\n" +
                        "urgbgurbwbggbguubuuugwbbrurwrrrrwurwwbwgurgwug\n" +
                        "gwwrubburgrggrrrbgwrurgguwrruguururbrrbwuwwgburggub\n" +
                        "ubwbruwgggggggbububruwrwbwuwguwgrurrgwugrbbgwwbgr\n" +
                        "brgrrrubwuwrubugwubwbubguwrruruwwugrwgwwr\n" +
                        "rruwrgruwuwgbgubuwgbgrbruwgbbgrbugwwwbrurgr\n" +
                        "gbbuuubrbbgrbuggwgubrggbbugrrrgwrrwbggruwbguugrurwgwrwwbr\n" +
                        "grrrggubrbwbgwgwbbwwgwrwrbruwbuurbuguwgwuwgrguurguuwub\n" +
                        "ugugwwbrruwubwbgbrwrrrwubguuwrwrbrururugwuuubw\n" +
                        "ubggrbggwwrbwbrrwgwruuruwgurgbrwurwwbbbbrrbrbrbgbbbbrw\n" +
                        "ggwguuuwgrbugggurgrwwwburrwuwubrrbwururbrbgwrww\n" +
                        "rurrurgrgguwrwgguuugwuurwbgwrgwugubbbwbbgwbbrwguurrurbw\n" +
                        "brgurgbwbgubbgruwwwuwbuwwbbwbbwuubbwgurwwgrbwbrrwrgwgrru\n" +
                        "bwuuggurbwwbgbbugbgburbbwgbbuuugggrrbrgwgrgurbbuw\n" +
                        "bbgbbrrgbgubrrbububugrgurgburrwgruugggrrgwgbuwbuugwuwwu\n" +
                        "brgbugbuwrbuuwrbggbggbbgggwubwwuuwrurwwgggbuwrwwrrb\n" +
                        "rbuguggbbbbrubbugbgurgugbwgurwrbgguuuubrgugbu\n" +
                        "brgbugbrrrurbwwgurrubwrrggggwwburubwrrbubw\n" +
                        "brgbwwgwbwwrgguwgguuuwwruwwbrrwrrrwrbuwbgrb\n" +
                        "rugwgwggwurwbgwubuwbrrgrgrrwguruugbbwbwbrbbubwurububurrwu\n" +
                        "rrrurbrrrururwruggwrugbbwurrrwburgrrggrbggbggubrwrg\n" +
                        "wbwurrruuwgwuwwgbuwwwgurgbrbwbguubggwrrubgwbgrubgggrgwgg\n" +
                        "uwurwuwurrgwbwbwbgburbwbwrbgwrrwuggrbbrrgrggwwbww\n" +
                        "brgwwgbbggbugubwrwrgbrrurrbrburgrugwwbwwuuggwuww\n" +
                        "bggwgurrwbuguuugwrgbuubuwbwgbwgbbrrbwbwgwr\n" +
                        "brgwgugbrgrbugruruwgrbguwgbbrbrwgbrrwbguuwgugwgwrgwgu\n" +
                        "urbbgurrburbuuwwuubrwwwubgrbbgrwgbbruurrggwrwgbbuwr\n" +
                        "gwrubugrurbbrgrbbwruuwwrgrgrbwwgbuwuuurwuuwrwg\n" +
                        "rwwugbubrrgwbrbbwrgburuwbrbgurbbwrrwburwbbbwguub\n" +
                        "grubbwbubwwbbwrwrrububbubrbwgugurububbrubwbrb\n" +
                        "brgbrrurrbrgbgggwwrbwrgwwbwwgbuwwbgwugrgbrrugwrurwbwbuggww\n" +
                        "brgurrbguuwrwwubwbugubburbgrguwrgrgwrrbgrubugrbgrugbrrrr\n" +
                        "rrurggrwrwwurwububbggbguubbgwwwrwguuwgwggb\n" +
                        "brubrgwgwbbwburgwbrggbgbwgwbrbuuubwwbrwbrbbgg\n" +
                        "ubrwrurwgugbrwbuurbwruwrrwgguuuugubwbuwbgrbgrbgwwrbrbgu\n" +
                        "wrburwwubrrgrbbgrggrwwwuwuwgwbbgwwggwrrgwggrwrguwrbg\n" +
                        "brgwugwrugbgwwuwgrbgbgwgwgwubrgbugwwgrgbg\n" +
                        "wrrgwurguwugubwubuuubwwbubuuguubgbrbbuubuggbb\n" +
                        "wbrugrwrbwwuubgwwuruuubbgbwgrugrrrwrwrbrurrrbbguuuwbuwwub\n" +
                        "brguurrbwgrrrubruwwuwbgugwgrwrurbwbbururrrgurgwrrrgbrgrr\n" +
                        "rugubuuggrrbbbbrgrrwrgburrrwrwgbbuwrggbrrubbwug\n" +
                        "gbbrgrubggwuuruwubbubbrgrgrgbrrwrgurrbbbuugbgurwbgrbbwbwr\n" +
                        "bwwgwubrrwwuwgwggwruuwugwrgwbgwwwugbwwwbrgg\n" +
                        "ubwurubwggrrwbbubbrrruubbggwbguuubbburbwuwwgrrwbwrgbbw\n" +
                        "brguwwbrwugubuwbbuwbgwbbgguurbwguwguwwbwubwbbruw\n" +
                        "buugbwwubgrururgwbwrgwwwgwuwuggurwrrggwuuwwguuggwrgg\n" +
                        "wbrbrrugwurgwwgwuwggbrwwugwruwgwgrbubruwugwwuubwgguuwbbwb\n" +
                        "wwubggbbwrrrrgrrugbbugbruuwgbrbgbbgbuuwbuuubbbgr\n" +
                        "brguurggwgugrrwuwuwrruugwwrwgugubbggbgrggru\n" +
                        "buugwggguwruwbgubbgurrwwrwbbbgbuuwrbubrbbr\n" +
                        "rwgwgbwgwbwwrbwgbggrbwgurrgbuwgurwggwbbwuuwuubugwwbwbggwu\n" +
                        "ggwwwwwgbuguwrrbwbrrbgwbbgbbwuugrbbrbwbbbr\n" +
                        "brgrwbgwrbgrwgbbuwuggbuubuwbwgbwbwurbbuwggbuuww\n" +
                        "brgwubrgburruwrgwgrurbbgguuwwgbgurggubgugrwrgwwrbggrgwg\n" +
                        "grrwwurwbwuwbugburgbguwburwrruggrrwwgrbgggubuurgwwrwwuww\n" +
                        "ggwwgrbruuurgbgrbbbgurwwgwbbgwgrbbbbbrgubgur\n" +
                        "gwbwurguwwwwrggwwwuburwwwgbguugrugwguubwwrgbwuwbrurrbwuwg\n" +
                        "brgrurwubgbgrbwwrwbgrgwgwrgwuguwubggwrgbgubrruwb\n" +
                        "wuubguubbgbrrwuguurrbuguwwgugbuuuuugbguububruuu\n" +
                        "bubwrrwbubrurwubgbrbbwuurwwwwuwgrrbwbwguurguuugg\n" +
                        "ubggburbgwugrbruuggrruguuwburgwbgwubwuuwrgrrwbbrwruuwwgg\n" +
                        "rwubbrbugrugwugrrrugrgrgwuubwrbrwuwggrurgrrgwrwbr\n" +
                        "burubuwbwbbbwgbwrbgugwwbwwubbbbggubrbrwwwbgrbwwrwuuubbuwbw\n" +
                        "wbgwrwrwwbggwbrubgrwbbguwrgwggbbbgbbugwgwurwgugbwrwbwgbr\n" +
                        "rrbbrgrbwbruwwgbwwrwgrrwbbguuggggrgubrrwrgb\n" +
                        "brgrgubgbgurbbuuwbrwbbbwgwuwugggrrbrgbwgwwbg\n" +
                        "gwwrwuwbbubrwrgwbrbbburrrwugururgugwbbwgbgrg\n" +
                        "brubrgrbggrwugbgbgurbrburgubrgbgwrwrggrbgwgbggwwgwwbgu\n" +
                        "brgbbwgrbguwbruwuurbbbwbwwrurburgggbbwrrgwgwbwb\n" +
                        "brgrugwbwbwbgwrubbrgbguwwbwgwgugbwwuurguur\n" +
                        "brwwwbgwuwgubwwwgrruggggrubgurwrwwuwrgwwguw\n" +
                        "rwwubrgruwwwrwwwwuurggwbubwbrruuwurwbrgurgbgg\n" +
                        "brgugrguwburbugrwrugwgbuuurrwgrwrugurrubuwrrurwurruurubw\n" +
                        "rbuwwuwbgwbgrgwwwbwuburwrgwurbwbgugbwuuuurwuwuugrurgubu\n" +
                        "wurruugrbubbgrrgwgbruwrwbrbrwurruwugurbuurrwbr\n" +
                        "uruuuuwwbbwggurwbuwrgrggwuwwwbgubuuuubrwbgwrbbrbbubrwgbb\n" +
                        "ubgbgrgggrbgbuwubwugbwwuuruuuurugwbggrwwuwbgbwwugrrbwwrb\n" +
                        "ggrbrwwrwbwggwbgrbwwurwbwwbgwugrrrrgbuwrbbgwubgbwruwb\n" +
                        "brgrguwwbwwubbwgwwwrugwwwrbgrwrrwwurrrrwgg\n" +
                        "brguubwgwuubgbwrwgubggrbbbubrwbuwburuwgurrburgug\n" +
                        "rggubgwbwwgrbbgbrrwrbgrrggbrurwrbwgrubrwuugug\n" +
                        "brgubuuurbrbbbrubwuubgwwbrurwubwgrwrugrgbruur\n" +
                        "bbrruuubrbgrbwrbbgwwrwurbggbwgbgubwbbbgwbwbwwrgguugbw\n" +
                        "rbggrwgrrrwwwbgugbbrwgrwbwwrrruwbgbbwbbuwuww\n" +
                        "bgwgbrruguwrbrrwugwgbuwwrurwwurrwwuuubggbbugubbrgwrugu\n" +
                        "brgbrbgbwggwguggwguwgrrbgwuwrwbgwubgbrwwggubburrurwggbbgbbr\n" +
                        "wgbbbuwrrrwgggugwgbuwuuuwwguwugugwbrbwwugwuwwwguu\n" +
                        "urbwurbuuurbwggrrrggbbgwguuuuwwwbbwuwuurgbgruuwugbuu\n" +
                        "gbrwbwuuwgwrggguwuurwrrgugbwwbrwgubwbwubwuurrwbgurgrwrg\n" +
                        "brgbrwbgruuururwubrbuubbrubugbwrrbggurguugbbgwubwurbubwbgbw\n" +
                        "rwwuwwbrgburbbbbwrrrugwrubwgruruburuwwrwbwgwgwgguuugw\n" +
                        "rrwbwgbgubwbrguwugbwbwuwubggwbugbgbuuwggwugbbbrbrurr\n" +
                        "ggruwwwgwrwgbubrbgurgrugbbgbbbuugggguburwwgrbrbgwrrgbgr\n" +
                        "uwgbrbuwwgubbgrwbggguurubwubwwgrwwgbwbbwurg\n" +
                        "bbguwbwrugguuugwwguubwurbuuurwgwrwgrgbrbrburugwurwgbwgbgrg\n" +
                        "rwwbrwgggrguubgbrrbuubrguurgwwggbrwbrwrgwrguwrggur\n" +
                        "wwruguurgrguwbwuguuuuuggrgwbgbgwbwwugugbrwg\n" +
                        "gubwbburguwuguubbugbrrgubrgbbwuwwurgugbwugbwr\n" +
                        "rgbrburrwgrbbuubrwbrbwrurgbwgrrbwbbbrwrggbrr\n" +
                        "rwgggwbuggrgbwgubuugwubuggrrwuwwguggwrwuggwrwrugrur\n" +
                        "ugububwbwgbwwggbgbgrbbbwurgguuwurbrbrwrrbuu\n" +
                        "rbbgbwgugguwrurgrgwwururbwrwuwwgwbbwugrbrugbgwbgurbubgugww\n" +
                        "uuwrububugwguwugwwwubwggrguurrruurwuwbbrwbwurbggwuuuwrgru\n" +
                        "ggbrrggwbwwuuruwrrbuuwrbbgwwwbuurugrrggbrb\n" +
                        "brgrwuuwgrwwbbwwrbubgubgrrurubwgbbwgrwbuurgubuurrubburg\n" +
                        "rbgwwrwwgwwuggrwrwuurguurbbwuwugbwugrubwbugrbbgrrrbubugrgb\n" +
                        "gugrrgbbgrrggbuwgwwuwbwrwbgbbgwubrguuurwwbbuwbuwgbwwgubb\n" +
                        "brgrwwbbrwbwrbwgugubwugbggwgurwguwgbugrurwrwgwbu\n" +
                        "brgrwwwwbbgrrurwbuubbwgbbrrbbrbwuwburrwgwuu\n" +
                        "bgrbuuwruwubbuuuurgwuwbgrbbrggruwwrbuuwrbuggbu\n" +
                        "bwrrwruubbbubbugwwrwbrwgububwguggbrgrrurgurgubggbbr\n" +
                        "brgwwgbrbuwbguuwubrgbruuruubrwwrubgurwwrubgr\n" +
                        "uwgrrbbrgrruggrurrwruwbggggbrrubugrrbugbwwgg\n" +
                        "wbubgwgwwwgwggrwgbbwbgubbbggrruuuwuurwurbgr\n" +
                        "rrbwguubgrwbgruruwgrrggwwwgwgggubbuwuwwugrwbbruurwbrrr\n" +
                        "wrbbuubgrbwrguwbwurgugrguwwgbwbrwgubbwgrrgwwwuwwgbggw\n" +
                        "rwrbuggbbwuwwrbubugurbbrbubbgbbuggwgrrgwgwgbbrbguub\n" +
                        "brguuwgrbubbrbwwuugwgguurrguubbubgwbrbbwrwbbbuurgrrrb\n" +
                        "brgrubrggrurwuuuuububgruugwwurgwwwrggbbwbbuwrug\n" +
                        "brgrgrbgrwgwgbwrwbgrbwgbbwrbgrbuggbwbwuggur\n" +
                        "wbggrgubbwwbwbwbggbbrurwggwrrruggwurrurbbbbgbbb\n" +
                        "brguwgrgwggubrugbuguruubgrgbugwuuwwgwubwbbbrgurbuwg\n" +
                        "brgbbuuuwbwuwrwuwuwgwruurrggggbgwggwwruwbwbrwrruubrwbgu\n" +
                        "buwugrbubwwwgwurbwruuwrubggbwubbgrwbwrwrurgbrug\n" +
                        "uwugugbrbruubwrrbubrwruwbwgggbgwrugggbwrubburubbrb\n" +
                        "brwwugbrurrbubwwgbugbbbbgrwwwwbugrrgguwgwrgwwwubr\n" +
                        "bbrwgwrrugrrgrbugruurgburrwbrwuwrbuwuuuuguwgbur\n" +
                        "wuubbrwwgbrgugbgugrbrgrgwbwuwuwbgbbrrrbguggbrr\n" +
                        "bbuwbggrggwrgwbubbuuwguwwrwgruggbrrwubbwwrgrgwruguwwggwwgu\n" +
                        "gubrbrrubgrgrbwwrwgbburruurgurwrwgwbguurwrbrwwbruuuru\n" +
                        "gburgwgggbbgruubwwgguggurrbwbrbrwwwgwbbuuwbuguubwwgggwww\n" +
                        "brgrbrbbugbbguwrbugubwwwwgrwrbgwuwwugrwwggruuruwrwwb\n" +
                        "uwwgbwwgwrgwrgwwwuurrrugburgrurbwggbbrwruwwrgwgugg\n" +
                        "wwubugugbrurbbggurrguggwwuguurbrugbbwurubbbgwrrwbb\n" +
                        "rwbbugbwrugrwrwwrruwwbbrugubbbgbrubruurrwuuugwurwggb\n" +
                        "gbrrbwgrruwrrgbwwwbgwrrgbuwbrrbugrwwbubrwbrgrgwrww\n" +
                        "gbbwwrgrbbggugbgwrugwwbgurgwwbwwwrwruwguuurgwbugburg\n" +
                        "brgubuguruwurrgugbrwrwbwugwbrrrggwwrbuwrrwrbrrggburrbwg\n" +
                        "brgwuuwubuwguwubgbwgwwrubwwuwbrbgwugbguuubbuggbww\n" +
                        "uwgbwurgwwrgururrwbwwruuwuuuggruggbwgugbgrub\n" +
                        "bbrrgggwwgguuggugurwwbgrwugbwwugbrgbrggwrgbggwurbwwubug\n" +
                        "brguubwwuugwggubwbbbrrwrwubggruuuubugrrugbwwgrubwbggugwwbuuu\n" +
                        "wuuuurbrrgrrrrubuggbburrrbwwgurrurbbwuugwrrwrubru\n" +
                        "bbuuwwuubgwbrwrwwbugrgwguwuuuubbgbwrrgurugggbguu\n" +
                        "wwgwwwugrrggbgwgwubuwuwugbubbbbbrgwwruurrgrugrrgwrrgrgb\n" +
                        "ubwrgrggbrrruruwbguwrurwuurugggwrburrubggbgbuuuwg\n" +
                        "brguwrgwbwrwuwwbwuuwrgbuuuuwgwwgwbbgwguwwgrwbuuuwwbrwbbr\n" +
                        "brgrrwrwubwwbwbuuwgwubgbbgbgwwgrrrugbgru\n" +
                        "brgbbruggubugugubuubwuwgwgwgwugwggrbwubbw\n" +
                        "rruuwgbggrwbuuggrubgwwubbwrrwrbwubgbrwgurbbgrgg\n" +
                        "gbwgwuguwrrbwrrrurbuguuggwurbbruruwuburrgwbgrwwb\n" +
                        "ubuurguurwrbrwurruuwgrgwwrwbwbrubbugbrbbubg\n" +
                        "brgbwwbgwwwrwrwwbrgbgbrrbbgrwrbrruwbgrggrruwu\n" +
                        "brgwggbwwgurrbwbgbugrbuwwugbgggwwwrbbuggbrbr\n" +
                        "brgbuguwbwwugrgrbgbuurrbuurggwwwubwwrbbburrrrrbrgrggrrwubruw\n" +
                        "gbrgwwruurggwbwbbugbwbwubbguubgwrbbwrrwbrwu\n" +
                        "rrgggrggugggrgggggrwrwrwrrgbrggurbrburrbbwwurggbub\n" +
                        "brgugwugubuwwgugrwgrwgruurbggbguwgbguguurwburwg\n" +
                        "gbwgugrwrbwrbuwrrwrugrwwwrbubwwwrggrrguuugrgu\n" +
                        "brgbgugwrubrurrrbwubgbgwwrggrubrbuwuuruuwgbwgrbrrwuurwuw\n" +
                        "brgrgrrrrruburgwwbubwbrwrubrwrwuwbgrrbgrbguuuurubwwb\n" +
                        "brgwbwgubwrbuwrrwuggrwrubbbwurburrgwruwgurgugrwgubrrugwu\n" +
                        "brgrwbwbrubbubgrubbbguwgguwrgwbgurgbgrwrgrburuwgbrrw\n" +
                        "brgbgwbbbgurrgbgwwuuwugwrugrggwubbgurbgbuuwrwwuggbrggbbuwwr\n" +
                        "brgwuurgbuggggwwrgbgwguwgbbuguggggwugrwbw\n" +
                        "brgbgrbrgbbubwwggwwugbrrrgbbugguuguwgrgrrwu\n" +
                        "brgbuwrggrggggbbwbbwuwgbwbwbbubbuggrgbuwwgub\n" +
                        "wrwuguugrbuggggrubrgwgwbuuburgbuggwrwrwuugurrugrbru\n" +
                        "brgrrgrrrbgubrwuurgurbugwwrwggubgwugguuu\n" +
                        "rbrwggbbrbbbguuwrgugrrwurbugrwwgwugbwugbwbwuwgw\n" +
                        "bwuwwrugrwwubbbugurbrbrwrubbwrbwbbrgrgrwuu\n" +
                        "rrruurrubbuuubuwgurrbruubwbbgurwuwgrgwubugggwbgurwbbggr\n" +
                        "brgwgwwbwugrrwurrrurugbrggwrwuuuubwbrrrgrwbrugwwuwgwr\n" +
                        "bgwuuuwwrrgwuuurwbrwrrgrwgugwgwubugbrugbgrbwgbrr\n" +
                        "brgrbuwbgbgbugwwruwururrggwgbwggwguubguubwuwruubgbrwwgg\n" +
                        "ubuwrwrgrrrrurrwubggwuurrguwwrgggubwbbugwbwug\n" +
                        "wbrrrruwubrrrgrrgwgubrwrbwwrbbuugwwbuuggwwrugggrwgg\n" +
                        "wwbrruwbbgrwgwrguwrbrgrbguurubbwuwggrwrbbgbwwwbbrguuurgrwb\n" +
                        "rbwrrwrwwgruububgugbuwrubwbuwuuurrbwbwuuwgwggrgb\n" +
                        "rgbwgruubgwruwuwrbububwbrguuurrgbrwbgbwwrrbgbbru\n" +
                        "brgrrwgbbgrbbuwwbrrruubbgbbbgguubrgrbuwbwwwrbwgggwurwurwww\n" +
                        "brgurggwwurbwbrgurgrrggrwwwbgubguuubgubwru\n" +
                        "wgrbbbwgubbguuuuguwbbuwwgwwbgrgrrrrrgbgrwwrwgubbb\n" +
                        "brguurbbrwurrurwbbwrbbwgbwrrgwrgbbguubgwruubbgug\n" +
                        "rubuguuurrwbwrrgbgbbrbwwubburbuwuwgbubuuur\n" +
                        "brgwurwrrgrgbrbwbggubwwwgwuuguuwurrggrubbwubuuwgb\n" +
                        "brguuuubruwgbwwrwrububgwwgruggbwwgrrugbbgugwuwgwwruggwgw\n" +
                        "brbbuubwuubgwguwbubrrbbgbwrrwuwgbwbbuuugbrbubrrwugwgbggbb\n" +
                        "brgbubgbggbrruurrwrgggguuwgubbrbwrrugubrrwrrbugguw\n" +
                        "gurrgbwwrbrggwrgbubgwurwwrbwurburuugwwgrbbgrgr\n" +
                        "uwubrgwuugbrgbguwwubwgrbuubbwgrbbrwwgrrrwrrwrggrww\n" +
                        "brgrrbrbruuguruwrbrurrggwugubwwrbrwrbbuggrrbrw\n" +
                        "buuuurgbuubrugguwggurrrwgguuurrbgubbggrwuurgugurwrgu\n" +
                        "bbguwuubwrgwwrruwrurwrugbbubrbuggbugbbuwwbubguwb\n" +
                        "rbbuwbwrbwugwbbrrgwubwbugbwwugguwbgrbgrbwrguguwbuwbrbbbgw\n" +
                        "brgrggubwwgrubwwgbuubrubrwwubwrwgurbugbbbwbggbrggbggg\n" +
                        "brgwwgrwuwububrugbbwwguurubwwwwuggrurbuburgrbgurugwb\n" +
                        "grggwbrugrwbgugbubbuburuwrgwwggbwggwururrwwgrrgwbwwr\n" +
                        "rbgbbwgbgwrbburbgbrubuwrbguwuubgrbwuwrrugbugrrur\n" +
                        "brgbbwwrbbgwbggrwrbuwbwgubbwgbuwggwrruwwgwwuwug\n" +
                        "urbgwrgrwrwwbuggwwggurrrgwwrbwubwwwbgwrbbwubbrrw\n" +
                        "ubgwgbwgbgwwbwbgrugwburrugwbwugruubbburbuwrbrrggbubbgg\n" +
                        "uugrbguwwuugwwwguguwrwrwburuugrbuuwgwgwuuuwgwgwwgwugwwugb\n" +
                        "gwrbrwurwggwuuguurwrbugguwuburguwwrwgburbuwbgugr\n" +
                        "gbgugrbgbggwbbwugwuuugbgbwrwwrwwrurrrbguww\n" +
                        "wgbbbwwrrruwggbgruububgurrurgrgrbgbbuwwurguwuggbggrbbgbw\n" +
                        "brgwuurubgggubbggwgwbwrubwubugwrgbgbgwbrr\n" +
                        "gwugbrwurruwubwbguuwwggbgwrrwrbgrbwrubwwbgbbbrgwubg\n" +
                        "wbgrgbubrbgbwbrgrburbrrwuuwuwgwwugggrgguwb\n" +
                        "brgwbwubbburgrwububgbwrwugwwwbrbwgrwuguwrwbgubguwuu\n" +
                        "gggggrwgggrwbugbrbgugggrgwbrrbrbgwbgurwuwgugbbgggrw\n" +
                        "bwrwuugwuuuwwgugrrgrrbrruwbgrugwgrrgrugrgrgr\n" +
                        "brguuuuwgbwwwuubbbrwwgbbubrurbrgbuwbwwrwwuurbrggbugrub\n" +
                        "bbburrgrugwggrgbwwwbwbrbwuwbrwbrbwgruwbubbw\n" +
                        "bggwgwwugbguwrggwwgrwgwwbgbwruguguwbgurrwbwrugu\n" +
                        "brgrwgrwrrrgwwugbgrbwuwwbwwwubbugbwgbuggggwbwwrrggwbrwrgwggg\n" +
                        "rbgugwwgruubguwwbgwgugugwurrugrbburuwbuwwurwggguugbbguur\n" +
                        "ruguguuuuwrbgwbuugubbruguuggwwruwrwgrggbbggrgrrugruubrw\n" +
                        "uuwuggruuuwwgbwrubgbuuugrbwbbwgwrbbggguguuwgbwwugwbg\n" +
                        "gugwwrurgrgrbburbuuubuwgbwwgurbuwburbugwbwgbuwrruwug\n" +
                        "rbrwgrurbrbrwbuwbwbggruwbruggruuwrwrwwrwwbgbwrbbburuuw\n" +
                        "wugguwgwgwwugrbgbrubgwwrurbwbwwggwbubrbrrurrurwrgugwugbgrr\n" +
                        "bgbgbbburbgrbbgbwrbwurgbwrurubwrwgrbwwwbwuwuwgrbuw\n" +
                        "rgurbwuugbwugrwbruugwuwrbbggbwrrgrwuuuggubr\n" +
                        "brgwwurruburubwgrgwrrwrggwbwguwrwbggggubgbuw\n" +
                        "wwuuwugbruwuurguwubburwbbbgubrgbggbbbwgwrubrgrrugg\n" +
                        "urburubububugwwrbbgrruwuguwrrrrubruurburuwuurubburgw\n" +
                        "rgbwggugwrbuubwwuuruuuwurwgbrbbrwgbrwgwuwbrgbrgbburruwwug\n" +
                        "grrwgbrgbwubgbbburwburbugbrgbrgurwbbuwbgrbgugr\n" +
                        "brgwugwrrbrgrwwwbguwurbrbbwgwbbwuwbrrurbrggwgr\n" +
                        "uwrrgwrbugurbggburbbrruubrrrrbururwgugbgguurugwrbwrr\n" +
                        "uubrwbbbugwggwruwrggwbgrwuwbrrgbbbrgbgwgggwbbrg\n" +
                        "wwrrbbuuwggbuwugrugrrwrrrwwbbwwrruubbwgguguwwuuuwg\n" +
                        "gwgbrurggwwrgguggguwubrwurwrggrubwrbrwgwwugu\n" +
                        "grurbgbgwwwwurbbrrwbuwuuurrgurrgbwgbbuuugbbrbw\n" +
                        "wrurwguguguwgwbbrbrguuuuggbrbugbbggbwgrwubwg\n" +
                        "wburuburbuubugrbugbuwburbgbwwbgrgbbbwubuubrw\n" +
                        "brgbuwbugguwbrgrrwwubbbburgrbuwwwgggbwwrugwgwbwbgb\n" +
                        "brgurbwubgrwbrubrbwuuubggurbwwgrrugrwruugugubgubg\n" +
                        "urgrurbrbgugrrggwrbrwwgubbguwrrggugwbbugrgrgbuwuruubbrgugb\n" +
                        "gbbbwrubwbgrbgubgwwuuuuwbwguuwbrwwgbggurbwbrubru\n" +
                        "guubugrrggggruwbgbuuggbbwrbrwrbbbugrgwwwrbburuurbggw\n" +
                        "brgwruubuwwrrgwbuwgburgwrwuubrubrwgwwruurgg\n" +
                        "wrrrubbubwbwuuuwuwubwwguwbggrbgrrbrgurrbgrrbugwbwbwgwurwuw\n" +
                        "ubbggbwuggurgggbbwbbgrrgrurbbrrurrrbrrwguuugw\n" +
                        "brgrurbrwwgbbrwwgbrgwugrbwubwugurggugbuwur\n" +
                        "brgugwbrwrwurggurbubgggguuuubgwwrgrbrwbrb\n" +
                        "brgwuwgrgwubbgwgburuuwruguugurubrbgubgbwgrubuwrr\n" +
                        "brwgggwggbrggrguugggrbgbrggwggrwbrwbwrrbgug\n" +
                        "uwwgbrbrrrrbbgwwubrrbuggguwrbuwwggruubbrwbwubgggruuw\n" +
                        "rwgwwuuggbbuwwubburrbguruurggrbubgrbrubrwubwwururgbb\n" +
                        "brgbuuurrwrbuwwubgrrubggbugbbrbwbgurubrggbu\n" +
                        "brgrwurugrgrgwgruuwrrrrubbrbbgrbwrgrgrrwwgwwwggwubwwgu\n" +
                        "wurbuuggubwwwbrwgbrrwugbuwwrguuugubruuwgbruwwrubg\n" +
                        "brgwwuuuuwrbruwurbubwrubuugggurbwrgbwgwbburbrbrwbrwggb\n" +
                        "uwbgbrurwbrbrurruguurggrbuwubbwwruuwuruggrbggwwwrbw\n" +
                        "gugbbggrubrgwrwrwwbbgbrubrwrgugruggbgwubuwgwwwrubggrwgu\n" +
                        "brgubgwbwrwrwugggrwrggrubgbuuugbgbwwwurrgrwruwgugbrb\n" +
                        "brgrruwuuugrrrwrwrwugbuwbuwrrwbbbrbubrbgrgbuuwuwwr\n" +
                        "brgwugggwrruuwurbrurwwbbgbrugguubrrububggguruubwbruugwbrrgr\n" +
                        "bwgwuwrgrbrwgbbbuwgrruurwrbburrwggubbuwwuubwuurrb\n" +
                        "brgbggurbguwubbbgurubgwgugwbbwurgwrrrubbrwrrwr\n" +
                        "brgbbwbuuggbuggbubwgbuggrgwwggwgugwbuuwwrwbugw\n" +
                        "wurbrrgguruwgwurgbbbgrbruuugwgrgwugwgurgbbwubggrwurgrwbu\n" +
                        "ggruwrrbgguwgwugggguguggubwgrwrgurwwubwrwb\n" +
                        "ugrubuwggrbbrwurbubgbbgrrwbrbrbrwbugrgggrwbrgrbwrbbuwwr\n" +
                        "brguubwwbbuwggbrrururugrurguggwubgggrbggbrw\n" +
                        "gbwwrbuwurwuurgggwbugwrbugwruwwbrrugwbwrbbwbwwuuugrwguwg\n" +
                        "brgrrugubwubgwbwuurruubbuggrruruurugrrbbwrwrgrwb\n" +
                        "bbgwbrgwgurrubwubbwwwwgugrrrwubwbwurbgubrrwbr\n" +
                        "gruubuubwubugbrwbbrwwuuwwrbbwrgrbrwgwrwrrgggguugbwwbwubu\n" +
                        "brgwbrgbgrgrwwbwugrugbuuwrugbbgurgbbrbrr\n" +
                        "brgwrburrbwwgwwwggwbgrwgbggburrrbwuwuwgrbrbwrggwbwugwu\n" +
                        "wgrrwwgurbugrwubbruugwgrwuggbgguuuruwwgbrrrbg\n" +
                        "rrrubugbrrgwruuuwwwwwuurugrbbrbuwgwuruggrugggwugbu\n" +
                        "brgrwruwrwrruuurggwwguubuwuggburrrrrbrbugrbrwruwwrur\n" +
                        "brgbbguggbgbbgbrugwuubbrbrrbugbbuwurggugrbgrbwrbrbw\n" +
                        "brguwggurwwwrugwrugruuwbbburgurruwwwrguwbbwuugbwrgbw\n" +
                        "guggggbuwgggrwwwrgwrbbwuruuurrburrwuwgugwu\n" +
                        "brgrugwbgrgurgbrbgbrrwrwrbrrwruruwurgurgrbbbrr\n" +
                        "rwwrwrubuuurrgrrgbwurgbbggubrrwrugrgrrbwrwuu\n" +
                        "wggwggrgrbbuwgrubwbbbbuwgrubgrubrwbgbgwrwbwuug\n" +
                        "brgbrugrwuuwgrwubgbuuubwbbwruggrggubrbbbgwg\n" +
                        "brgggwbguubbwbgrbbwbwrbuwgrbruuwrbbwubgrwubuwruwr\n" +
                        "rrgwrbgrwurwbgbwgbgbwgbrwugrubbrggbwwugwwgbrrw\n" +
                        "wggbgubbuwggbrrgwuwbwruwubrruguubwugrubgbbbwrugrrww\n" +
                        "gubuwuwrugbwggwruwrbggbuuwurgubrwubuwgrwrbbwuurggu\n" +
                        "brgwuwurgggurrgbrguguwbgrgguwrbrwwbguwubwrrrugrrwbrrrbrgu\n" +
                        "brgwwrwgbgwgbrwbrbbwwbbrrgbugurgrwggggwwrrgrb\n" +
                        "wrrrgguurubgurbgbwuwwrwgrbggrbwgrbrurgugbrg\n" +
                        "bwbbuurbwbwwrwggwwbwbrwrwrwgurwbbgbububrgugrubbbgwrb\n" +
                        "ruguggwruggbrbbgwbwuuruuwgwwbbuuwbbwwrwrwuuuugurw\n" +
                        "rggbbbuggruggwwwgrgrurgugbbrgrwwgubggwbrrgrbug\n" +
                        "bwggurbgwuuburuwwgwwbbrggurbururrgubbuwugurbbugruw\n" +
                        "bgrwbrrbbuubbruubrwuuuggubbguuuuruwrgrbbrbbbruurbrrwgbwbrg\n" +
                        "ubbbuwugbrwbwurbgwrrwgruurwurrbuurrrgggwurbuuwrgbrw\n" +
                        "brgrrurruwwruwrgrgwrwbwggwbrgruguugubggurwrrubug\n" +
                        "ubwubwwrrgbgrugbbgwbgrbuubguwwwbguwbbgrgbbbwgwg\n" +
                        "brgwbwrguugrrbguggwwbubrgwbrrgbbbgbguugguuugugugrruwuwrr\n" +
                        "wbugggwbrwuwururubwuugguwgurrurgggrbrbwrwgwgubbbgrrbgu\n" +
                        "wgurwubbruburgrwuurrbwgwuwrrggubrbguuuwgwbugubbu\n" +
                        "brgrbwrbrbuwururbuurgbrggwwgrbgrwbgbwrwggburwrw\n" +
                        "grbuwrbugbwuwwggbrwgbrubwgrwwrwrwruuwwubuwurwwgrbuguru\n" +
                        "ggwggguuwbbubgguurrwburgugbrrwruubguwrwrruugrwwwwrbbbbbr\n" +
                        "brgwbwgrbubgrrbuuguggwwgrrrgwwwuuuwggwgwggwwrurbg\n" +
                        "bbugbrwgugrururruubbrubrubrgwuwguggbguubburwurbrbrgbbrug\n" +
                        "brgbbbwwruubrbuurwgrgggggubrbbbwbbrubgrurrgrw\n" +
                        "wwgrubwbrwrwwrgurbuwbgrrrgruuburwurrgbrwbggb\n" +
                        "wbuwwuubbwguugrbwwguwruubuuwugwgwgwuuwguggggugwrbubgrwgu\n" +
                        "wrubuggubbubrgbbwbuuggrbrgrbbbgbbrgbguuwgrgrurrwb\n" +
                        "rrrrwbrbrbrrrwbwuwwwbwubugburbwgruwrwbwurbuugwbuw\n" +
                        "rguwrugwbruuggrbgwbbbbbrbbrbguwbguruugurgw\n" +
                        "uwbugwgwrbgguubggwwbrwrrrrbwbbguuurwwwwwurrugg\n" +
                        "ggrrbrwgrwrgbbrbwwrrwbugguwgugwuuguwbbgbuuggbggwggubrr\n" +
                        "ugbuguugwwrubgururggwuguururwubbggbgwbubbrbbubburbwu\n" +
                        "brgwbbggbbggwurgwruwuuuurrbururubbwuurbwwbrugbubuwrggwrwww\n" +
                        "wbuwgbgwgbbrwrgwrrgbrwbugwbgurrrwwbrrbguwgrubuwrubbbb\n" +
                        "uwgrggggwbrbwgruurgubrugbuubbuugubwbbrbrrbwuruwr\n" +
                        "gwurubrgwgwbgwrbwwbrwbwuwrrwwurbwwuuuwbwubugbrguwb\n" +
                        "brguwrgrgubrgrbuuguurgugbgbbwwwrrbrwgwbguuruwgrguub\n" +
                        "gbggrwrguuwugwbwbrubrrgwwwbruwrbwbuggggrguwwrguwgurwbu\n" +
                        "brbwuurrrrbrguwururrgwrbbwwgbruwrbwgwbuwwgr\n" +
                        "bgwbbwwrgrgugwururrrrwbggwbrbbbwrwgwggbbrrrguwuuwgg\n" +
                        "brgrwgrrrgubuubwwbubruwwgbuggbbuggggwbruwubrurbbuguu\n" +
                        "bruwbwgbwrbbubuubugwwwwwuruubwugbrrubuwgwrwbwbwrbgw\n" +
                        "uggwbuwrgrugwgwbbguubbggwggbuuguwruwrggururwrrubrwwggw\n" +
                        "wgbugbbrurwubrbrurgrgubwguggrwrgubuwwwugbbggubwrrrrbggbbrr\n" +
                        "brgugwugruwuuugurgwbwbgrrwwbbuuugrrwrbbgugrrurgrr\n" +
                        "ugubrrbugrrubgugbwrwwbuurguubbrurrrguubbuwburgg\n" +
                        "brbuwbuubgbruwgrggwwgwgrbrgugggwwgbrgruurrruburgugw\n" +
                        "brgwubrrwruuuuuuwurbrbgbuuwwgguwwrrgwrbrw\n" +
                        "brguuwrwwruwguwbbrwgurgrwrrbwwbugrrurgwwwbwubwgbg\n" +
                        "brguugbuuugurwwbgrrgbgwwbbgrrwuuwgrwbbubrru\n" +
                        "ugbgggbrrurbrrwgbbwgwubwbrrrggguurrggrbwruwrugwwbwburbgg\n" +
                        "uuururwuuggrrgwwrbuwggwbwuurwgrwuwgbuwrwbbwurub\n" +
                        "brgrrrrrrwrgwgbrgbbbwgrgwrrbbgwwbubuubuugruwrwrg\n" +
                        "rrbbrggwgugwwgwburwwubbugbwgurgbrwbwrgwrbgggrg\n" +
                        "rgggwwwurgwbwgugwwuurbrwuuwrurrrrgurgwwugwgrb\n" +
                        "brgwbbubwbgubrrwwuuubuwbbuuubrwbwuwwrgrgrgggb\n" +
                        "gguubbuuggrbbwbrwrugrwuruugubuwguurrwurwbugwrgwgwguuguruuu\n" +
                        "brgbbrbruubbgwgubuubruwbwbrguubgwwguguuu\n" +
                        "brgugwwrbwubwwrbrwbbrrwrgbgwugwurrgugbuwgrrwgb\n" +
                        "brgwuwbguwgbrrurgguugrrwbwurwgwbgbuubwwuuw\n" +
                        "brgbrwbrbruubbgbruuwruwwubrbuguubgwrwurruwgwrrwrwuubbrur\n" +
                        "brgbgurwbwwrbrgrgggrwbwururwgbugugbwgubwbgbwugugwrwwubggbbbg\n" +
                        "ggwggggwgurgwbubrwgugwuuggwwuwrguubwrbggubbgrwbruubbuwgggg\n" +
                        "bbwgwbugbgrgbguwbrwwwgwrrbbbuwwrgruwbwgbbbbgburgwugwrwrwgb\n" +
                        "gubbgrrrwrwrbwrbrrubgubggrwwrguwrbuwrrwguruurwrwguuuwwgrgg\n" +
                        "brguugwugbburrubggrububwbuwrgrurrgwgbgwwr\n" +
                        "wuwbbgrwwrrgubgwbwwwbwruwwgbrbruggrrrubrubbw\n" +
                        "rururwwgrbwbubuwwrurruurrbrbwwwwgwrgrbugwurbwr\n" +
                        "rgrrwwuubruugwwgwrbwubwgrrburgubrwuwwrwwrbbbgrrwubrgugr\n" +
                        "gbwrbwgurgbwggurrwbuuwbbuwurggwggrrbwwugbwrrwgrbb\n" +
                        "bwgrwuggwbuubbrbguggugubwrruwbbwrwggurubwwwwbbbrbubub\n" +
                        "brguuuwrrwwgbgwuwrgruggggggwurburubgwbrrrguguwwrgrbggrbbwg\n" +
                        "brgurburbwwubggbguwgugwurwuwbwgrwrruuurbbwbrruu\n" +
                        "brgrbgrwrbwugbrbbgwwguururbbububrwgubrbw\n"
        );
    }

    @Override
    public List<Comparable<?>> getExampleOutput1() {
        return List.of(6, 267);
    }

    @Override
    public List<Comparable<?>> getExampleOutput2() {
        return List.of();
    }

    @Override
    public Comparable<?> solvePartOne(Stream<String> lines) {
        var linesIterator = lines.iterator();

        var availableTowelPatterns = new HashMap<Character, Set<Map.Entry<String, String>>>();
        String[] inputAvailablePatterns = linesIterator.next().split(", ");
        for (String availablePattern : inputAvailablePatterns) {
            for (int i = 0; i < availablePattern.length(); i++) {
                char stripe = availablePattern.charAt(i);
                String leftStripes = availablePattern.substring(0, i);
                String rightStripes = availablePattern.substring(i + 1);
                availableTowelPatterns.computeIfAbsent(stripe, k -> new HashSet<>())
                        .add(new AbstractMap.SimpleImmutableEntry<>(leftStripes, rightStripes));
            }
        }

        linesIterator.next(); // empty line

        int possibleDesigns = 0;
        for (String line = linesIterator.next(); linesIterator.hasNext(); line = linesIterator.next()) {
            boolean patternPossible = true;
            for (int i = 0; i < line.length(); i++) {
                char stripe = line.charAt(i);
                boolean stripePossible = false;
                for (var availablePattern : availableTowelPatterns.getOrDefault(stripe, Set.of())) {
                    String leftStripes = availablePattern.getKey();
                    String rightStripes = availablePattern.getValue();
                    if (i - leftStripes.length() >= 0 && leftStripes.equals(line.substring(i - leftStripes.length(), i))
                            && i + rightStripes.length() + 1 <= line.length() && rightStripes.equals(line.substring(i + 1, i + rightStripes.length() + 1))) {
                        stripePossible = true;
                        break;
                    }
                }
                if (!stripePossible) {
                    patternPossible = false;
                    break;
                }
            }
            if (patternPossible) {
                System.out.println(line);
                ++possibleDesigns;
            }
        }

        return possibleDesigns;
    }

    @Override
    public Comparable<?> solvePartTwo(Stream<String> lines) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
