/*******************************************************************************
 * Copyright 2015
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.tudarmstadt.ukp.dkpro.core.io.text;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Lemma;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
//import de.tudarmstadt.ukp.dkpro.core.morpha.MorphaLemmatizer;
//import de.tudarmstadt.ukp.dkpro.core.testing.IOTestRunner;
//import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;

public class TokenizedTextWriterTest
{
    /* Commented out because dependency on io-tokit cannot be satisfied without creating a cyclic dependency.
   @Test
    public void testDefault()
        throws UIMAException, IOException
    {
        File targetFile = new File("target/TokenizedTextWriterTest.out");
        targetFile.deleteOnExit();
        File tokenized = new File("src/test/resources/tokenizedTexts/textTokenized.txt");

        String line1 = "This is the 1st sentence .";
        String line2 = "Here is another sentence .";

        CollectionReaderDescription reader = createReaderDescription(StringReader.class,
                StringReader.PARAM_DOCUMENT_TEXT, line1 + "\n" + line2,
                StringReader.PARAM_LANGUAGE, "en");
        AnalysisEngineDescription segmenter = createEngineDescription(BreakIteratorSegmenter.class);
        AnalysisEngineDescription writer = createEngineDescription(TokenizedTextWriter.class,
                TokenizedTextWriter.PARAM_TARGET_LOCATION, targetFile);
        SimplePipeline.runPipeline(reader, segmenter, writer);

        assertTrue(FileUtils.contentEquals(tokenized, targetFile));
    }
    */

    /* Commented out because dependency on io-tokit cannot be satisfied without creating a cyclic dependency.
    @Test
    public void testTokens()
        throws UIMAException, IOException
    {
        File targetFile = new File("target/TokenizedTextWriterTokensTest.out");
        targetFile.deleteOnExit();

        String line1 = "This is the 1st sentence.";
        String line2 = "Here is another sentence.";
        String typeName = Token.class.getTypeName();// + "/coveredText()";
        File tokenized = new File("src/test/resources/tokenizedTexts/textTokenized.txt");

        CollectionReaderDescription reader = createReaderDescription(StringReader.class,
                StringReader.PARAM_DOCUMENT_TEXT, line1 + "\n" + line2,
                StringReader.PARAM_LANGUAGE, "en");
        AnalysisEngineDescription segmenter = createEngineDescription(BreakIteratorSegmenter.class);
        AnalysisEngineDescription writer = createEngineDescription(TokenizedTextWriter.class,
                TokenizedTextWriter.PARAM_TARGET_LOCATION, targetFile,
                TokenizedTextWriter.PARAM_FEATURE_PATH, typeName);
        SimplePipeline.runPipeline(reader, segmenter, writer);

        assertTrue(FileUtils.contentEquals(tokenized, targetFile));

    }
    */
    
    /* Commented out because dependency on io-tokit cannot be satisfied without creating a cyclic dependency.
    @Test
    public void testLemmas()
        throws UIMAException, IOException
    {
        File lemmatized = new File("src/test/resources/tokenizedTexts/textLemmatized.txt");
        File targetFile = new File("target/TokenizedTextWriterTestLemmas.out");
        targetFile.deleteOnExit();

        String text = "This is the first sentence. Here is another sentence with more words and tokens.";
        String typeName = Lemma.class.getTypeName() + "/value";

        CollectionReaderDescription reader = createReaderDescription(StringReader.class,
                StringReader.PARAM_DOCUMENT_TEXT, text,
                StringReader.PARAM_LANGUAGE, "en");
        AnalysisEngineDescription segmenter = createEngineDescription(BreakIteratorSegmenter.class);
        AnalysisEngineDescription lemmatizer = createEngineDescription(MorphaLemmatizer.class);
        AnalysisEngineDescription writer = createEngineDescription(TokenizedTextWriter.class,
                TokenizedTextWriter.PARAM_TARGET_LOCATION, targetFile,
                TokenizedTextWriter.PARAM_FEATURE_PATH, typeName);
        SimplePipeline.runPipeline(reader, segmenter, lemmatizer, writer);

        assertTrue(FileUtils.contentEquals(lemmatized, targetFile));
    }
    */

    /* Commented out because dependency on io-tokit cannot be satisfied without creating a cyclic dependency.
    @Test
    public void testStopwords()
        throws UIMAException, IOException
    {
        File targetFile = new File("target/TokenizedTextWriterNoStopwords.out");
        targetFile.deleteOnExit();
        File tokenized = new File("src/test/resources/tokenizedTexts/textTokenizedNoStopwords.txt");
        List<String> expected = Files.readAllLines(tokenized.toPath());

        String line1 = "This is the 1st sentence .";
        String line2 = "Here is another sentence .";
        String stopwordsFile = "src/test/resources/stopwords_en.txt";

        CollectionReaderDescription reader = createReaderDescription(StringReader.class,
                StringReader.PARAM_DOCUMENT_TEXT, line1 + "\n" + line2,
                StringReader.PARAM_LANGUAGE, "en");
        AnalysisEngineDescription segmenter = createEngineDescription(BreakIteratorSegmenter.class);
        AnalysisEngineDescription writer = createEngineDescription(TokenizedTextWriter.class,
                TokenizedTextWriter.PARAM_TARGET_LOCATION, targetFile,
                TokenizedTextWriter.PARAM_STOPWORDS_FILE, stopwordsFile);
        SimplePipeline.runPipeline(reader, segmenter, writer);

        assertEquals(expected, Files.readAllLines(targetFile.toPath()));
    }
    */

    /* Commented out because dependency on io-tokit cannot be satisfied without creating a cyclic dependency.
    @Test
    public void testNumbers()
        throws UIMAException, IOException
    {
        File targetFile = new File("target/TokenizedTextWriterNoStopwords.out");
        targetFile.deleteOnExit();
        File tokenized = new File("src/test/resources/tokenizedTexts/textTokenizedNoNumbers.txt");
        List<String> expected = Files.readAllLines(tokenized.toPath());

        String line1 = "This is 1 sentence.";
        String line2 = "Here is 2 sentences, or even 2.5.";
        String numbersRegex = "^[0-9]+(\\.[0-9]*)?$";

        CollectionReaderDescription reader = createReaderDescription(StringReader.class,
                StringReader.PARAM_DOCUMENT_TEXT, line1 + "\n" + line2,
                StringReader.PARAM_LANGUAGE, "en");
        AnalysisEngineDescription segmenter = createEngineDescription(BreakIteratorSegmenter.class);
        AnalysisEngineDescription writer = createEngineDescription(TokenizedTextWriter.class,
                TokenizedTextWriter.PARAM_TARGET_LOCATION, targetFile,
                TokenizedTextWriter.PARAM_NUMBER_REGEX, numbersRegex);
        SimplePipeline.runPipeline(reader, segmenter, writer);

        List<String> result = Files.readAllLines(targetFile.toPath());
        assertEquals(expected.size(), result.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }
    */
    
    /* Commented out because dependency on io-tokit cannot be satisfied without creating a cyclic dependency.
    @Test
    public void testNumbersAndStopwordsAndLemmas()
        throws UIMAException, IOException
    {
        File lemmatized = new File(
                "src/test/resources/tokenizedTexts/textLemmatizedNoStopwordsNoNumbers.txt");
        File targetFile = new File("target/TokenizedTextWriterTestLemmasNostopwordsNoNumbers.out");
        targetFile.deleteOnExit();
        List<String> expected = Files.readAllLines(lemmatized.toPath());

        String text = "This is 1 sentence. Here are 2 sentences.";
        String typeName = Lemma.class.getTypeName() + "/value";
        String numbersRegex = "^[0-9]+(\\.[0-9]*)?$";
        String stopwordsFile = "src/test/resources/stopwords_en.txt";

        CollectionReaderDescription reader = createReaderDescription(StringReader.class,
                StringReader.PARAM_DOCUMENT_TEXT, text,
                StringReader.PARAM_LANGUAGE, "en");
        AnalysisEngineDescription segmenter = createEngineDescription(BreakIteratorSegmenter.class);
        AnalysisEngineDescription lemmatizer = createEngineDescription(MorphaLemmatizer.class);
        AnalysisEngineDescription writer = createEngineDescription(TokenizedTextWriter.class,
                TokenizedTextWriter.PARAM_TARGET_LOCATION, targetFile,
                TokenizedTextWriter.PARAM_FEATURE_PATH, typeName,
                TokenizedTextWriter.PARAM_STOPWORDS_FILE, stopwordsFile,
                TokenizedTextWriter.PARAM_NUMBER_REGEX, numbersRegex);
        SimplePipeline.runPipeline(reader, segmenter, lemmatizer, writer);

        List<String> result = Files.readAllLines(targetFile.toPath());
        assertEquals(expected.size(), result.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }*/
}