package GENWINHOTKEY;

import java.util.*;

public class GokuFull {
    public static Map<String, String> sf = new HashMap<>();
    public static Map<String, String> s = new HashMap<>();
    public static Map<String, String> f = new HashMap<>();
    public static Map<String, String> onlyD = new HashMap<>();
    public static Map<String, String> caps = new HashMap<>();
    final static String charSymbols = "qwertyuioplkjhgazxcvbnm1234567890";
    final static List<String> stringSymbols = Arrays.asList("delete_or_backspace", "hyphen", "slash", "backslash", "equal_sign", "semicolon", "period", "comma", "quote", "open_bracket", "close_bracket", "spacebar");

    final static int tapMs = 150;


    public static void main(String[] args) {
        putDefaultAllJustCapsValsToMaps();
        putNonDefaultValsToMaps();

        //1. default template header
        System.out.println(header());
        //2. to 'caps' for each charSymbol & stringSymbol
        for (char c : charSymbols.toCharArray()) {
            System.out.println(full(c + ""));
        }
        for (String s : stringSymbols) {
            System.out.println(full(s));
        }
        System.out.println(footer());
    }

    public static void putDefaultAllJustCapsValsToMaps() {
        //for all symbols
        // in each map -> +{%s}
        for (char c : charSymbols.toCharArray()) {
            caps.put(c + "", String.format("!S%s", c + ""));
        }
        for (String s : stringSymbols) {
            caps.put(s, String.format("!S%s", s));
        }
    }

    public static void putNonDefaultValsToMaps() {


        sf.put("i", "!Spage_up");
        sf.put("k", "!Spage_down");
        sf.put("j", "!Sleft_arrow");
        sf.put("l", "!Sright_arrow");
        sf.put("delete_or_backspace", "!Odelete_or_backspace");

        s.put("i", "!Sup_arrow");
        s.put("k", "!Sdown_arrow");
        s.put("j", "!SOleft_arrow");
        s.put("l", "!SOright_arrow");
        s.put("delete_or_backspace", "delete_forward");

        f.put("i", "page_up");
        f.put("k", "page_down");
        f.put("j", "left_arrow");
        f.put("l", "right_arrow");
        f.put("delete_or_backspace", "!Odelete_or_backspace");

        onlyD.put("i", "up_arrow");
        onlyD.put("k", "down_arrow");
        onlyD.put("j", "!Oleft_arrow");
        onlyD.put("l", "!Oright_arrow");
        onlyD.put("c", "!Tc");
        onlyD.put("v", "!Tv");
        onlyD.put(";", "!T;");
        onlyD.put("z", "!Tz");
        onlyD.put("x", "!Tx");
        onlyD.put("a", "!Ta");
        onlyD.put("delete_or_backspace", "delete_or_backspace");

        caps.put("delete_or_backspace", "delete_or_backspace");
    }


    public static String full(String c) {
        List<Map<String, String>> mapL = Arrays.asList(caps, onlyD, f, s, sf);
        List<String> l = new ArrayList<>();
        String curr = null;
        for (Map<String, String> m : mapL) {
            if (m.containsKey(c)) curr = m.get(c);
            l.add(curr);
        }
        return String.format(
                """
                        [:!E%s :%s]
                        [:!EF%s :%s]
                        [:!EFW%s :%s]
                        [:!EFS%s :%s]
                        [:!EFSW%s :%s]
                        """,
                c, l.get(0),
                c, l.get(1),
                c, l.get(2),
                c, l.get(3),
                c, l.get(4)
        );

    }

    public static String header() {
        return """
                {      \s

                        :profiles
                 {:uwuth {:default true
                         :sim     10
                         :delay   10
                         :alone   """.concat(" " + tapMs + "").concat("""

                        :held    30}}
                       :main [{:des "move with ijkl SF"
                            :rules [
                                   \s

                 ;caps->E, d->F, s->S, f->W
                [:##caps_lock :right_option] ;E
                [:!Ed :!Efn nil {:alone :!Sd}] ;F
                [:!EFs :!EFleft_shift nil {:alone :!Es}] ;S
                [:!EFf :!EFright_control nil {:alone :!Tf}] ;W
                [:!EFSf :!EFSright_control nil {:alone :!ESf}]
                [:!EFWs :!EFWleft_shift]
                             
                [:!Es :!Ss]
                [:!Ef :!Sf]
                """);
    }

    public static String footer() {
        return """
                ]
                }
                ]}""".indent(21);
    }

}
