package my.custom.udf;

public class replace {
    public static void main(String[] args) {
        String input = "\u0000\u0000\u0000\u0000\u0005<row><c1>1</c1><c2>2</c2><c3>3</c3></row>";
        String output = input.replaceAll("\\x00", "");

        System.out.println(output);
    }
}
