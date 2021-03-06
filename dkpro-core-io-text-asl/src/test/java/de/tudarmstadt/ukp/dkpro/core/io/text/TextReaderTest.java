/*
 * Copyright 2017
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
 */
package de.tudarmstadt.ukp.dkpro.core.io.text;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.factory.TypeSystemDescriptionFactory.createTypeSystemDescription;
import static org.apache.uima.fit.util.CasUtil.select;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.util.CasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import de.tudarmstadt.ukp.dkpro.core.api.io.ResourceCollectionReaderBase;
import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;

public class TextReaderTest
{
	private static final String FILE1 = "test1.txt";
	private static final String FILE2 = "test2.txt";
	private static final List<String> FILES = Arrays.asList(FILE1, FILE2);

	@Test
	public void fileSystemReaderTest()
		throws Exception
	{
		CollectionReaderDescription reader = createReaderDescription(TextReader.class,
				ResourceCollectionReaderBase.PARAM_SOURCE_LOCATION, "src/test/resources/texts",
				ResourceCollectionReaderBase.PARAM_PATTERNS, "[+]*.txt");

		for (JCas jcas : new JCasIterable(reader)) {
			DocumentMetaData md = DocumentMetaData.get(jcas);
            dumpMetaData(md);

            assertEquals(1, CasUtil.select(jcas.getCas(), jcas.getDocumentAnnotationFs().getType())
                      .size());
            assertTrue(FILES.contains(md.getDocumentId()));

			assertTrue(
					!FILE1.equals(md.getDocumentId()) || (
							"This is a test.".equals(jcas.getDocumentText()) &&
							15 == md.getEnd()));

			assertTrue(
					!FILE2.equals(md.getDocumentId())
					|| "This is a second test.".equals(jcas.getDocumentText()));
		}
	}

	@Test
	public void fileSystemReaderAbsolutePathTest()
		throws Exception
	{
		CollectionReaderDescription reader = createReaderDescription(TextReader.class,
				ResourceCollectionReaderBase.PARAM_SOURCE_LOCATION, new File("src/test/resources/texts").getAbsolutePath(),
				ResourceCollectionReaderBase.PARAM_PATTERNS, new String[] {
					ResourceCollectionReaderBase.INCLUDE_PREFIX + "*.txt" });

		for (JCas jcas : new JCasIterable(reader)) {
			DocumentMetaData md = DocumentMetaData.get(jcas);
            dumpMetaData(md);

            assertEquals(1, select(jcas.getCas(), jcas.getDocumentAnnotationFs().getType()).size());

            assertTrue(FILES.contains(md.getDocumentId()));

			assertTrue(
					!FILE1.equals(md.getDocumentId()) || (
							"This is a test.".equals(jcas.getDocumentText()) &&
							15 == md.getEnd()));

			assertTrue(
					!FILE2.equals(md.getDocumentId())
					|| "This is a second test.".equals(jcas.getDocumentText()));
		}
	}

	@Test
	public void fileSystemReaderTest3()
		throws Exception
	{
        CollectionReaderDescription reader = createReaderDescription(TextReader.class,
				createTypeSystemDescription(),
				ResourceCollectionReaderBase.PARAM_SOURCE_LOCATION, "src/test/resources/name with space",
				ResourceCollectionReaderBase.PARAM_PATTERNS, new String[] {
					ResourceCollectionReaderBase.INCLUDE_PREFIX + "*.txt" });

		for (JCas jcas : new JCasIterable(reader)) {
			DocumentMetaData md = DocumentMetaData.get(jcas);
            dumpMetaData(md);

            assertEquals(1, select(jcas.getCas(), jcas.getDocumentAnnotationFs().getType()).size());

            assertTrue(FILES.contains(md.getDocumentId()));

			assertTrue(
					!FILE1.equals(md.getDocumentId()) || (
							"This is a test.".equals(jcas.getDocumentText()) &&
							15 == md.getEnd()));

			assertTrue(
					!FILE2.equals(md.getDocumentId())
					|| "This is a second test.".equals(jcas.getDocumentText()));
		}
	}

	@Test
    public void fileSystemReaderTest2()
        throws Exception
    {
        CollectionReaderDescription reader = createReaderDescription(TextReader.class,
                createTypeSystemDescription(),
                ResourceCollectionReaderBase.PARAM_SOURCE_LOCATION, "src/test/resources/texts",
                ResourceCollectionReaderBase.PARAM_PATTERNS, new String[0]);

        for (JCas jcas : new JCasIterable(reader)) {
            DocumentMetaData md = DocumentMetaData.get(jcas);
            dumpMetaData(md);

            assertEquals(1, select(jcas.getCas(), jcas.getDocumentAnnotationFs().getType()).size());

            assertTrue(FILES.contains(md.getDocumentId()));

            assertTrue(
                    !FILE1.equals(md.getDocumentId())
                    || "This is a test.".equals(jcas.getDocumentText()));

            assertTrue(
                    !FILE2.equals(md.getDocumentId())
                    || "This is a second test.".equals(jcas.getDocumentText()));
        }
    }

	@Test
    public void fileSystemReaderTest4()
        throws Exception
    {
        CollectionReaderDescription reader = createReaderDescription(TextReader.class,
                createTypeSystemDescription(),
                ResourceCollectionReaderBase.PARAM_SOURCE_LOCATION, "classpath:texts",
                ResourceCollectionReaderBase.PARAM_PATTERNS, new String[0]);

        for (JCas jcas : new JCasIterable(reader)) {
            DocumentMetaData md = DocumentMetaData.get(jcas);
            dumpMetaData(md);

            assertEquals(1, select(jcas.getCas(), jcas.getDocumentAnnotationFs().getType()).size());

            assertTrue(FILES.contains(md.getDocumentId()));

            assertTrue(
                    !FILE1.equals(md.getDocumentId())
                    || "This is a test.".equals(jcas.getDocumentText()));

            assertTrue(
                    !FILE2.equals(md.getDocumentId())
                    || "This is a second test.".equals(jcas.getDocumentText()));
        }
    }

	private void dumpMetaData(final DocumentMetaData aMetaData)
	{
        System.out.println("Collection ID: "+aMetaData.getCollectionId());
        System.out.println("ID           : "+aMetaData.getDocumentId());
        System.out.println("Base URI     : "+aMetaData.getDocumentBaseUri());
        System.out.println("URI          : "+aMetaData.getDocumentUri());
	}

	@Rule public TestName name = new TestName();
}
