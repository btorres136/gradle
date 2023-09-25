package idl;

import java.io.Serializable;
import java.util.ArrayList;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectories;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.process.ExecSpec;

import groovy.transform.Internal;

import org.gradle.api.Action;
import java.io.File;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;

public class BuildTask extends DefaultTask {
    private final ConfigurableFileCollection source = getProject().files();
    private final DirectoryProperty outputDirectory = getProject().getObjects().directoryProperty();


    @OutputDirectory
    public DirectoryProperty getOutputDiretory()
    {
        return outputDirectory;
    }

    @InputFiles
    @PathSensitive(PathSensitivity.NAME_ONLY)
    @SkipWhenEmpty
    public ConfigurableFileCollection getSource()
    {
        return source;
    }


//omniidl -bcxx test.idl

    @TaskAction
    public void compile()
    {
        getProject().exec(new Action<ExecSpec>() {
            @Override
            public void execute(ExecSpec execSpec) {
                execSpec.executable("omniidl");
                execSpec.args("-bcxx");
                execSpec.args(source);
                execSpec.args("-C" , outputDirectory);
            }
        });
    }

}
