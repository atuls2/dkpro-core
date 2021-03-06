/*
 * Copyright 2013
 * Ubiquitous Knowledge Processing (UKP) Lab and FG Language Technology
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.tudarmstadt.ukp.dkpro.core.api.io;

import java.util.List;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;

import de.tudarmstadt.ukp.dkpro.core.api.resources.MappingProvider;

/**
 * Creates Chunk annotations from IOB encoded data.
 * For example, the sequence (B-NP I-NP) will be converted into a NP-chunk
 * annotation spanning two tokens.
 * 
 *
 */
public class IobDecoder
{
    private CAS cas;
    private Feature chunkValue;

    private MappingProvider mappingProvider;

    private boolean internTags = true;
    
    private String openChunk;
    private int start;
    private int end;
    

    public IobDecoder(CAS aCas, Feature aChunkValue, MappingProvider aMappingProvider)
    {
        super();
        cas = aCas;
        chunkValue = aChunkValue;
        mappingProvider = aMappingProvider;
    }

    public void setInternTags(boolean aInternTags)
    {
        internTags = aInternTags;
    }
    
    public void decode(List<? extends AnnotationFS> aTokens, String[] aChunkTags)
    {
        int i = 0;
        for (AnnotationFS token : aTokens) {
            // System.out.printf("%s %s %n", token.getCoveredText(), aChunkTags[i]);
            String fields[] = aChunkTags[i].split("-");
            String flag = fields.length == 2 ? fields[0] : "NONE";
            String chunk = fields.length == 2 ? fields[1] : null;

            // Start of a new hunk
            if (chunk == null || !chunk.equals(openChunk) || "B".equals(flag)) {
                if (openChunk != null) {
                    // End of previous chunk
                    chunkComplete();
                }

                if ("O".equals(flag)) {
                    openChunk = null;
                }
                else {
                    openChunk = chunk;
                }
                
                start = token.getBegin();
            }

            // Record how much of the chunk we have seen so far
            end = token.getEnd();
            
            i++;
        }
        
        // End of processing signal
        chunkComplete();
    }
    
    private void chunkComplete()
    {
        if (openChunk != null) {
            Type chunkType = mappingProvider.getTagType(openChunk);
            AnnotationFS chunk = cas.createAnnotation(chunkType, start, end);
            chunk.setStringValue(chunkValue, internTags ? openChunk.intern() :
                openChunk);
            cas.addFsToIndexes(chunk);
            openChunk = null;
        }
    }
}
