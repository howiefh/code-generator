package io.github.howiefh.generator.vcs;

import com.google.common.io.Files;
import io.github.howiefh.generator.common.exception.GitException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;

/**
 * @author fenghao on 2016/5/23
 * @version 1.0
 * @since 1.0
 */
public class Gits {
    private static final Logger LOGGER = LoggerFactory.getLogger(Gits.class);
    public static final String GENERATOR_BRANCH = "generator";
    public static final String WORKSPACE_BRANCH = "workspace";
    public static final String MASTER_BRANCH = "master";
    public static final String GIT_IGNORE = "resolve.bat\nresolve.sh";
    public static final String UTF8 = "UTF-8";
    public static final File DEFAULT_REPO_PATH = new File(System.getProperty("user.dir")
            + File.separator
            + ".generator");

    private File repoPath;
    private Git git;

    private Gits(File repoPath, Git git) {
        this.repoPath = repoPath;
        this.git = git;
    }

    public static Gits init() throws GitException {
        return init(null);
    }

    /**
     * 初始化工程
     * <p/>
     * <ol>
     * <li>初始化 Git 仓库</li>
     * <li>新建.gitignore文件</li>
     * <li>执行初始提交 </li>
     * <li>创建{@link Gits#GENERATOR_BRANCH}和{@link Gits#WORKSPACE_BRANCH}分支</li>
     * <li>并切换到{@link Gits#GENERATOR_BRANCH}分支</li>
     * </ol>
     *
     * @param repositoryPath
     * @throws GitException
     */
    public static Gits init(File repositoryPath) throws GitException {
        try {
            File repoPath = repositoryPath;
            if (repoPath == null) {
                repoPath = DEFAULT_REPO_PATH;
            }
            Git git = Git.init().setDirectory(repoPath).call();
            FileUtils.setHidden(repoPath, true);
            Files.write(GIT_IGNORE, new File(repoPath + File.separator + ".gitignore"), Charset.forName(UTF8));
            ;
            Gits gits = new Gits(repoPath, git);
            gits.add();
            gits.commit("init");
            gits.branchCreate(GENERATOR_BRANCH)
                    .branchCreate(WORKSPACE_BRANCH);
            gits.checkout(GENERATOR_BRANCH);
            return gits;
        } catch (IOException e) {
            throw new GitException(e);
        } catch (GitAPIException e) {
            throw new GitException(e);
        }
    }

    /**
     * 通过判断{@link Gits#DEFAULT_REPO_PATH}是否存在.git目录，且是否存在{@link Gits#GENERATOR_BRANCH}分支引用文件，判断是否已经初始化Git仓库
     *
     * @return
     */
    public static boolean isInitialized() {
        return new File(Gits.DEFAULT_REPO_PATH, ".git").exists()
                && new File(Gits.DEFAULT_REPO_PATH, ".git" + File.separator + "refs" + File.separator + "heads" + File.separator + GENERATOR_BRANCH).exists();
    }

    /**
     * 打开仓库，并切换到{@link Gits#GENERATOR_BRANCH}分支
     *
     * @return
     * @throws GitException
     */
    public static Gits open() throws GitException {
        return open(DEFAULT_REPO_PATH);
    }

    /**
     * 打开仓库，并切换到{@link Gits#GENERATOR_BRANCH}分支
     *
     * @param repoPath
     * @return
     * @throws GitException
     */
    public static Gits open(File repoPath) throws GitException {
        try {
            if (repoPath == null) {
                throw new GitException("Repo path is null!");
            }
            Git git = Git.open(repoPath);
            Gits gits = new Gits(repoPath, git);
            gits.checkout(GENERATOR_BRANCH);
            return gits;
        } catch (IOException e) {
            throw new GitException(e);
        }
    }

    public Gits add() throws GitException {
        try {
            git.add().addFilepattern(".").call();
        } catch (GitAPIException e) {
            throw new GitException(e);
        }
        return this;
    }

    public Gits commit(String message) throws GitException {
        commit(System.getProperty("user.name"), "user.email", message);
        return this;
    }

    public Gits commit(String name, String email, String message) throws GitException {
        try {
            git.commit().setAuthor(name, email).setMessage(message).call();
        } catch (GitAPIException e) {
            throw new GitException(e);
        }
        return this;
    }

    public Ref head() throws GitException {
        try {
            return git.getRepository().findRef(Constants.HEAD);
        } catch (IOException e) {
            throw new GitException(e);
        }
    }

    public Gits branchCreate(String branchName) throws GitException {
        try {
            git.branchCreate().setName(branchName).setStartPoint(MASTER_BRANCH).call();
        } catch (RefAlreadyExistsException e) {
            throw new GitException(e);
        } catch (InvalidRefNameException e) {
            throw new GitException(e);
        } catch (RefNotFoundException e) {
            throw new GitException(e);
        } catch (GitAPIException e) {
            throw new GitException(e);
        }
        return this;
    }

    public Gits checkout(String branchName) throws GitException {
        try {
            git.checkout().setName(branchName).call();
        } catch (GitAPIException e) {
            throw new GitException(e);
        }
        return this;
    }

    public Gits beforeMergeWorkspace(Map<File, File> fromTos) throws GitException {
        checkout(WORKSPACE_BRANCH);
        for (Map.Entry<File, File> ft : fromTos.entrySet()) {
            copy(ft.getValue(), ft.getKey());
        }
        add();
        commit(WORKSPACE_BRANCH + " : " + new Date());
        checkout(GENERATOR_BRANCH);
        return this;
    }

    public void copy(File src, File dest) {
        try {
            Files.copy(src, dest);
        } catch (IOException e) {
            LOGGER.warn("copy {} to {} error! Exception: {}", src, dest, e);
        }
    }

    public Gits mergeWorkspace(String mergeStrategy) throws GitException {
        return mergeWorkspace(MergeStrategy.get(mergeStrategy));
    }

    public Gits mergeWorkspace(MergeStrategy mergeStrategy) throws GitException {
        //workspace 和 generator 分支都指向合并后的提交
        merge(mergeStrategy, WORKSPACE_BRANCH);
        checkout(WORKSPACE_BRANCH);
        merge(mergeStrategy, GENERATOR_BRANCH);
        return checkout(GENERATOR_BRANCH);
    }

    public Gits merge(MergeStrategy mergeStrategy, String branch) throws GitException {
        try {
            git.merge().setCommit(false).setStrategy(mergeStrategy).include(git.getRepository().findRef(branch)).call();
        } catch (IOException e) {
            throw new GitException(e);
        } catch (GitAPIException e) {
            throw new GitException(e);
        }
        return this;
    }

    public static void main(String[] args) {
        try {
            Gits gits = Gits.open(DEFAULT_REPO_PATH);
            gits.checkout(GENERATOR_BRANCH);
        } catch (GitException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
