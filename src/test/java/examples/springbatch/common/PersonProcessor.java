
package examples.springbatch.common;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class PersonProcessor implements ItemProcessor<Person, Person> {
    
    private ExecutionContext executionContext;

    @Override
    public Person process(Person person) throws Exception {
        incrementRowCount();
        
        Person transformed = new Person();
        transformed.setId(person.getId());
        transformed.setFirstName(person.getFirstName().toUpperCase());
        transformed.setLastName(person.getLastName().toUpperCase());
        return transformed;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        executionContext = stepExecution.getExecutionContext();
    }
    
    @BeforeChunk
    public void beforeChunk(ChunkContext chunkContext) {
        incrementChunkCount();
    }

    private void incrementRowCount() {
        executionContext.putInt("row_count",
                executionContext.getInt("row_count", 0) + 1);
    }

    private void incrementChunkCount() {
        executionContext.putInt("chunk_count",
                executionContext.getInt("chunk_count", 0) + 1);
    }
}
