package ui;


import com.netcracker.unc.team35.task_manager.model.TaskModel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

public class OutputStreavTaskView implements TasksOutput {

    public OutputStreavTaskView(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    private OutputStream outputStream;

    @Override
    public void update(Collection<TaskModel> allTasks) throws IOException {
        //TODO:print table of tasks
        outputStream.write("tasks list:".getBytes());

    }

    @Override
    public void out(String s) throws IOException {
        outputStream.write(s.getBytes());
    }
}
