package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unify4j.model.process.StreamProcess;

import java.io.File;

public class Executor4j {
    protected static final Logger logger = LoggerFactory.getLogger(Executor4j.class);
    private String _error;
    private String _out;

    /**
     * Getter for the error message.
     *
     * @return Error message as a String.
     */
    public String getError() {
        return _error;
    }

    /**
     * Getter for the output message.
     *
     * @return Output message as a String.
     */
    public String getOut() {
        return _out;
    }

    /**
     * Checks if there is an error message.
     *
     * @return true if there is an error message, false otherwise.
     */
    public boolean isError() {
        return String4j.isNotEmpty(this.getError());
    }

    /**
     * Executes a command.
     *
     * @param command Command to be executed as a String.
     * @return Exit value of the process.
     */
    public int exec(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            return execute(process);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Error occurred executing command: {}, by exception: {}", command, e.getMessage(), e);
            }
            e.printStackTrace(System.err);
            return -1;
        }
    }

    /**
     * Executes a list of commands.
     *
     * @param list_command Array of commands to be executed.
     * @return Exit value of the process.
     */
    public int exec(String[] list_command) {
        try {
            Process process = Runtime.getRuntime().exec(list_command);
            return execute(process);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Error occurred executing command: {}, by exception: {}", joinCommand(list_command), e.getMessage(), e);
            }
            e.printStackTrace(System.err);
            return -1;
        }
    }

    /**
     * Executes a command with environment variables.
     *
     * @param command Command to be executed as a String.
     * @param env     Array of environment variables.
     * @return Exit value of the process.
     */
    public int exec(String command, String[] env) {
        try {
            Process process = Runtime.getRuntime().exec(command, env);
            return execute(process);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Error occurred executing command: {}, by exception: {}", command, e.getMessage(), e);
            }
            e.printStackTrace(System.err);
            return -1;
        }
    }

    /**
     * Executes a list of commands with environment variables.
     *
     * @param list_command Array of commands to be executed.
     * @param env          Array of environment variables.
     * @return Exit value of the process.
     */
    public int exec(String[] list_command, String[] env) {
        try {
            Process process = Runtime.getRuntime().exec(list_command, env);
            return execute(process);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Error occurred executing command: {}, by exception: {}", joinCommand(list_command), e.getMessage(), e);
            }
            e.printStackTrace(System.err);
            return -1;
        }
    }

    /**
     * Executes a command with environment variables in a specific directory.
     *
     * @param command Command to be executed as a String.
     * @param env     Array of environment variables.
     * @param dir     Directory where the command should be executed.
     * @return Exit value of the process.
     */
    public int exec(String command, String[] env, File dir) {
        try {
            Process process = Runtime.getRuntime().exec(command, env, dir);
            return execute(process);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Error occurred executing command: {}, by exception: {}", command, e.getMessage(), e);
            }
            e.printStackTrace(System.err);
            return -1;
        }
    }

    /**
     * Executes a list of commands with environment variables in a specific directory.
     *
     * @param list_command Array of commands to be executed.
     * @param env          Array of environment variables.
     * @param dir          Directory where the command should be executed.
     * @return Exit value of the process.
     */
    public int exec(String[] list_command, String[] env, File dir) {
        try {
            Process process = Runtime.getRuntime().exec(list_command, env, dir);
            return execute(process);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("Error occurred executing command: {}, by exception: {}", joinCommand(list_command), e.getMessage(), e);
            }
            e.printStackTrace(System.err);
            return -1;
        }
    }

    /**
     * Executes a process and captures its output and error streams.
     *
     * @param process Process to be executed.
     * @return Exit value of the process.
     * @throws InterruptedException if any thread has interrupted the current thread.
     */
    private int execute(Process process) throws InterruptedException {
        StreamProcess errors = new StreamProcess(process.getErrorStream());
        Thread errorGobbler = new Thread(errors);
        StreamProcess out = new StreamProcess(process.getInputStream());
        Thread outputGobbler = new Thread(out);
        errorGobbler.start();
        outputGobbler.start();
        int exitVal = process.waitFor();
        errorGobbler.join();
        outputGobbler.join();
        _error = errors.getResult();
        _out = out.getResult();
        return exitVal;
    }

    /**
     * Joins command strings into a single string.
     *
     * @param command Array of command strings.
     * @return Single string containing all commands.
     */
    private String joinCommand(String[] command) {
        StringBuilder s = new StringBuilder();
        for (String cmd : command) {
            s.append(cmd);
            s.append(' ');
        }
        return s.toString();
    }
}
