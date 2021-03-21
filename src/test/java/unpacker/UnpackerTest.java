package unpacker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnpackerTest {

    @Test
    public void testUnpackerConstructor() {

        //----------------проверка на ввод недопустимой строки-------------------------
        // ввод кириллицы
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[dф]"));
        // ввод ","
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[dd,fdg]"));
        // ввод "."
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[d.dfdg]"));
        // ввод "-"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf-dg]"));
        // ввод "!"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddfd!g]"));
        // ввод "?"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddfd?g]"));
        // ввод "}"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf}dg]"));
        // ввод "{"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[dd{fdg]"));
        // ввод "("
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf(dg]"));
        // ввод ")"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf)dg]"));
        // ввод "*"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf*dg]"));
        // ввод "^"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf^dg]"));
        // ввод ":"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf:dg]"));
        // ввод ";"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf;dg]"));
        // ввод """
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[dd\"fdg]"));
        // ввод "'"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf\'dg]"));
        // ввод "\"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf\\dg]"));
        // ввод "|"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[dd|fdg]"));
        // ввод "/"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[dd/fdg]"));
        // ввод ">"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf>dg]"));
        // ввод "<"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf<dg]"));
        // ввод "+"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf+dg]"));
        // ввод "&"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf&dg]"));
        // ввод "%"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf%dg]"));
        // ввод "#"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf#dg]"));
        // ввод "@"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf@dg]"));
        // ввод "№"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[dd№fdg]"));
        // ввод "$"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[dd$fdg]"));
        // ввод "`"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf`dg]"));
        // ввод "~"
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3[ddf~dg]"));


        // число в неположенном месте
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("3df[dfg]"));
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("ds3[xc3]"));
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("df4[df]2"));
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("2"));
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("25"));

        // неверная расстановка скобок
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("df4[df]]"));
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("df4[[df]"));
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("4[]"));
        assertThrows(IllegalArgumentException.class,()-> new Unpacker("[df]"));

        //----------------проверка на ввод допустимой строки-------------------------
        assertDoesNotThrow(()-> new Unpacker("3[sdf]"));
        assertDoesNotThrow(()-> new Unpacker("24[xcx]"));
        assertDoesNotThrow(()-> new Unpacker("dfd3[dfs]"));
        assertDoesNotThrow(()-> new Unpacker("3[3[fd]]"));
        assertDoesNotThrow(()-> new Unpacker("ds3[ds4[df]]"));
        assertDoesNotThrow(()-> new Unpacker("df2[5[fdg]fd]"));
        assertDoesNotThrow(()-> new Unpacker("df2[5[fdg]fd]dfgf"));
    }

    @Test
    public void testUnpack(){

        assertEquals(new Unpacker("3[df]").unpack(), "dfdfdf");
        assertEquals(new Unpacker("3[2[df]k]").unpack(), "dfdfkdfdfkdfdfk");
        assertEquals(new Unpacker("10[df]").unpack(), "dfdfdfdfdfdfdfdfdfdf");
        assertEquals(new Unpacker("s3[df]fg2[pk]").unpack(), "sdfdfdffgpkpk");
        assertEquals(new Unpacker("3[df]ph").unpack(), "dfdfdfph");
        assertEquals(new Unpacker("3[2[fg]3[o]]").unpack(), "fgfgooofgfgooofgfgooo");
        assertEquals(new Unpacker("1[2[4[fg]]]").unpack(), "fgfgfgfgfgfgfgfg");
        assertEquals(new Unpacker("3[xyz]4[xy]z").unpack(), "xyzxyzxyzxyxyxyxyz");
        assertEquals(new Unpacker("2[3[x]y]").unpack(), "xxxyxxxy");
    }
}