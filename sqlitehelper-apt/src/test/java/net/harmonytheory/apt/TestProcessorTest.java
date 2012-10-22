package net.harmonytheory.apt;

import net.harmonytheory.android.slideshare.data.Oembed;
import net.harmonytheory.apt.processor.SqliteHelperProcessor;

import org.seasar.aptina.unit.AptinaTestCase;

public class TestProcessorTest extends AptinaTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // ソースパスを追加
        addSourcePath("src/test/java");
    }

    public void test() throws Exception {

        // テスト対象の Annotation Processor を生成して追加
    	SqliteHelperProcessor processor = new SqliteHelperProcessor();
        addProcessor(processor);

        // コンパイル対象を追加
        addCompilationUnit(Oembed.class);

        // コンパイル実行
        compile();
    }

}