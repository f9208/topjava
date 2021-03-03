package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimerTestRule implements TestRule {

    private static final Logger LOG = LoggerFactory.getLogger(TimerTestRule.class);

    private static StringBuffer sb = new StringBuffer();

    @Override
    public Statement apply(Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                long timerStart = System.currentTimeMillis();
                statement.evaluate();
                long timerStop = System.currentTimeMillis();
                upToLog(description, timerStart, timerStop);
            }
        };
    }

    public void upToLog(Description description, long timeStart, long timeStop) {
        LOG.info("test - {}, time execute: {} ms",
                description.getMethodName(), timeStop - timeStart);
        sb.append("test - ")
                .append(description.getMethodName())
                .append(", time execute: ")
                .append(timeStop - timeStart)
                .append(" ms\n");
    }

    public static void getBuffer() {
        LOG.info(sb.toString());
    }
}
