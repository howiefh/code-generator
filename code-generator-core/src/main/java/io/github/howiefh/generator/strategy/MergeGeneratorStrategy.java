package io.github.howiefh.generator.strategy;

import com.google.common.io.Files;
import io.github.howiefh.generator.common.config.TypeCfg;
import io.github.howiefh.generator.common.exception.GeneratorException;
import io.github.howiefh.generator.common.util.FreemarkerUtils;
import io.github.howiefh.generator.common.util.StringUtils;
import io.github.howiefh.generator.vcs.Gits;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fenghao on 2016/5/23
 * @version 1.0
 * @since 1.0
 */
public class MergeGeneratorStrategy extends OverrideGeneratorStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(MergeGeneratorStrategy.class);
    private Map<File, File> fromTos;
    private File repoPath;
    private Gits gits;

    public MergeGeneratorStrategy() throws GeneratorException {
        this(null);
    }

    public MergeGeneratorStrategy(File repoPath) throws GeneratorException {
        this.repoPath = repoPath == null ? Gits.DEFAULT_REPO_PATH : repoPath;
        fromTos = new HashMap<File, File>();
        if (Gits.isInitialized()) {
            gits = Gits.open(this.repoPath);
        } else {
            gits = Gits.init(this.repoPath);
        }
    }

    @Override
    protected File generateTargetFile(Map<String, Object> model, TypeCfg type) throws GeneratorException {
        File finalFile = super.generateTargetFile(model, type);
        String targetTemp = StringUtils.replaceEach(type.getPkg(), new String[]{"."}, new String[]{File.separator})
                + File.separator
                + type.getSuffix();
        File targetFile = new File(repoPath, FreemarkerUtils.generateString(model, targetTemp, UTF8));
        fromTos.put(targetFile, finalFile);
        return targetFile;
    }

    @Override
    protected void afterGenerate() throws GeneratorException {
        gits.checkout(Gits.GENERATOR_BRANCH);
        gits.add();
        gits.commit("Start merge : " + new Date());
        gits.beforeMergeWorkspace(fromTos);
        /*
        gits.mergeWorkspace(MergeStrategy.THEIRS);
        gits.commit("End merge : " + new Date());
        */
        StringBuilder stringBuilder = new StringBuilder();

        //merge
        stringBuilder.append("git merge -s recursive -X theirs ");
        stringBuilder.append("-m \"");
        stringBuilder.append("Merge ");
        stringBuilder.append(Gits.WORKSPACE_BRANCH);
        stringBuilder.append(" to ");
        stringBuilder.append(Gits.GENERATOR_BRANCH);
        stringBuilder.append("\" ");
        stringBuilder.append(Gits.WORKSPACE_BRANCH);
        stringBuilder.append("\n");

        // checkout workspace branch
        // merge generator
        // checkout generator
        stringBuilder.append("git checkout ");
        stringBuilder.append(Gits.WORKSPACE_BRANCH);
        stringBuilder.append("\n");
        stringBuilder.append("git merge ");
        stringBuilder.append(Gits.GENERATOR_BRANCH);
        stringBuilder.append("\n");
        stringBuilder.append("git checkout ");
        stringBuilder.append(Gits.GENERATOR_BRANCH);
        stringBuilder.append("\n");
        for (Map.Entry<File, File> ft : fromTos.entrySet()) {
            if (SystemUtils.IS_OS_WINDOWS) {
                stringBuilder.append("copy \"");
            } else {
                stringBuilder.append("cp \"");
            }
            stringBuilder.append(ft.getKey().getAbsolutePath());
            stringBuilder.append("\" \"");
            stringBuilder.append(ft.getValue().getAbsolutePath());
            stringBuilder.append("\"\n");
            /*
            try {
                Files.copy(ft.getKey(), ft.getValue());
            } catch (IOException e) {
                throw new GeneratorException(e);
            }
            */
        }
        String resolve = "resolve.sh";
        if (SystemUtils.IS_OS_WINDOWS) {
            resolve = "resolve.bat";
        }
        try {
            Files.write(stringBuilder.toString(), new File(repoPath, resolve), Charset.forName(Gits.UTF8));
        } catch (IOException e) {
            LOGGER.error("Create {} error!", resolve);
        }
    }
}
