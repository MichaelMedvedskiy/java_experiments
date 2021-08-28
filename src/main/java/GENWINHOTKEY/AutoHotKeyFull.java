package GENWINHOTKEY;


import java.util.*;

public class AutoHotKeyFull {
    //not <Character, String> , because as key can have "Backspace"
    public static Map<String, String> sf = new HashMap<>();
    public static Map<String, String> s = new HashMap<>();
    public static Map<String, String> f = new HashMap<>();
    public static Map<String, String> onlyD = new HashMap<>();
    public static Map<String, String> caps = new HashMap<>();
    final static String charSymbols = "qwertyuiop[]';lkjhgazxcvbnm,./1234567890-=\\";
    final static List<String> stringSymbols = Arrays.asList("Backspace");

    final static int tapMs = 150;

    public static void putDefaultAllJustCapsValsToMaps() {
        //for all symbols
        // in each map -> +{%s}
        for (char c : charSymbols.toCharArray()) {
            caps.put(c + "", String.format("+{%s}", c + ""));
        }
        for (String s : stringSymbols) {
            caps.put(s, String.format("+{%s}", s));
        }
    }

    public static void putNonDefaultValsToMaps() {


        sf.put("i", "+{PgUp}");
        sf.put("k", "+{PgDn}");
        sf.put("j", "+{Left}");
        sf.put("l", "+{Right}");
        sf.put("Backspace", "^{Backspace}");

        s.put("i", "+{Up}");
        s.put("k", "+{Down}");
        s.put("j", "+^{Left}");
        s.put("l", "+^{Right}");
        s.put("Backspace", "{Delete}");

        f.put("i", "{PgUp}");
        f.put("k", "{PgDn}");
        f.put("j", "{Left}");
        f.put("l", "{Right}");
        f.put("Backspace", "^{Backspace}");

        onlyD.put("i", "{Up}");
        onlyD.put("k", "{Down}");
        onlyD.put("j", "^{Left}");
        onlyD.put("l", "^{Right}");
        onlyD.put("c", "^{c}");
        onlyD.put("v", "^{v}");
        onlyD.put(";", "^{;}");
        onlyD.put("z", "^{z}");
        onlyD.put("x", "^{x}");
        onlyD.put("a", "^{a}");
        onlyD.put("Backspace", "{Backspace}");

        caps.put("Backspace", "{Backspace}");

    }

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
    }

    public static String full(String c) {
        //list<map> caps, d, s,f, sf
        //list<String> l
        //String curr = Null
        //for each in map
        //if by c not null
        //new curr
        //l add curr
        List<Map<String, String>> mapL = Arrays.asList(caps, onlyD, f, s, sf);
        List<String> l = new ArrayList<>();
        String curr = null;
        for (Map<String, String> m : mapL) {
            if (m.containsKey(c)) curr = m.get(c);
            l.add(curr);
        }

        return String.format("""
                $%s::
                if(spressed and fpressed) {
                    Send %s
                } else if(spressed) {
                    Send %s
                } else if(fpressed == 1)   {
                    Send %s
                } else if(dpressed == 1)   {
                    Send %s
                } else if(pressed == 1)   {     \s
                    Send %s\s
                } else {
                    Send {%s}      \s
                }
                Return
                """, c, l.get(4), l.get(3), l.get(2), l.get(1), l.get(0), c);
    }

    public static String header() {
        return """
                #NoEnv  ; Recommended for performance and compatibility with future AutoHotkey releases.
                ; #Warn  ; ASD Enable warnings to assist with detecting common errors.
                SendMode Input  ; Recommended  for  new scripts due to its superior speed and reliability.
                SetWorkingDir %A_ScriptDir%  ; Ensures a consistent starting directory.
                                
                SetCapsLockState, AlwaysOff
                                
                CapsLock::
                {
                    pressed := 1
                    KeyWait, CapsLock
                    pressed := 0
                }
                Return
                                
                                
                $d::
                {
                   \s
                    if !pressed {
                        Send {d}
                        Return
                    }
                                
                    dpressed := 1
                    timePressed = 0
                                
                    msAtPress := A_TickCount\s
                                
                    while(getKeyState("d","P")){
                        sleep 10
                       \s
                	}
                                
                    dpressed := 0
                    if(A_TickCount - msAtPress < """.concat(tapMs + "").concat("""
                        ) {
                        Send +{d}
                    }
                                
                    return
                }
                                
                $s::
                {
                    if(spresser) {
                        return
                    }
                                
                    if !dpressed {
                        if(pressed == 1) {
                            Send +{s}
                        } else {\s
                            Send {s}
                        } \s
                        return
                    }
                                
                    spressed := 1
                     while(getKeyState("s","P")){
                         if(getKeyState("f","P")){
                            fpressed = 1
                         } else if(!getKeyState("f","P")){
                            fpressed = 0
                         }
                                
                        sleep 10
                       \s
                	}
                    spressed := 0
                   \s
                    return
                }
                                
                $f::
                {
                    if(fpresser) {
                        return
                    }
                                
                    if !dpressed {
                        if(pressed == 1) {
                            Send +{f}
                        } else {\s
                            Send {f}
                        } \s
                        return
                    }
                                
                    fpressed := 1
                     while(getKeyState("f","P")){
                        if(getKeyState("s","P")){
                            spressed = 1
                         } else if(!getKeyState("s","P")){
                            spressed = 0
                         }
                                
                        sleep 10
                       \s
                	}
                    fpressed := 0
                                
                    return
                }
                """);
    }
}
