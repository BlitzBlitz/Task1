package com.example.Task1.aspect;


import com.example.Task1.Task1Application;
import com.example.Task1.data.Department;
import com.example.Task1.data.Worker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(Task1Application.class);

    @Around("execution (* com.example.Task1.service.*.*(..))")
    public Object loggAllMethods(ProceedingJoinPoint pJoinPoint) throws Throwable{

        String className = pJoinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = pJoinPoint.getSignature().getName();

        final StopWatch stopWatch = new StopWatch();

        LOGGER.info("Executing " + className + "." + methodName);
        stopWatch.start();
        Object result = pJoinPoint.proceed();
        stopWatch.stop();

        LOGGER.info("Execution time of " + className + "." + methodName + " is " + stopWatch.getTotalTimeMillis() + " ms");
        return result;
    }

    @Around("execution (* com.example.Task1.controller.DepartmentController.addDepartment(..))")
    public Object loggAddDepartmentMethod(ProceedingJoinPoint pJoinPoint) throws Throwable{

        final StopWatch stopWatch = new StopWatch();
        Department department = ((Department)(pJoinPoint.getArgs()[0]));
        LOGGER.info("Adding Department: " +  department);
        stopWatch.start();
        Object result = pJoinPoint.proceed();
        stopWatch.stop();
        LOGGER.info("Department: " + department + " added in : " + stopWatch.getTotalTimeMillis() + "ms");
        return result;
    }

    @Around("execution (* com.example.Task1.controller.DepartmentController.retrieveDepartments(..))")
    public Object loggRetrieveDepartmentsMethod(ProceedingJoinPoint pJoinPoint) throws Throwable{

        final StopWatch stopWatch = new StopWatch();
        LOGGER.info("Retrieving All Departments");
        stopWatch.start();
        Object result = pJoinPoint.proceed();
        stopWatch.stop();
        LOGGER.info("All departments retrieved in : " + stopWatch.getTotalTimeMillis() + "ms");
        return result;
    }

    @Around("execution (* com.example.Task1.controller.DepartmentController.retrieveDepartment(..))")
    public Object loggRetrieveDepartmentMethod(ProceedingJoinPoint pJoinPoint) throws Throwable{

        final StopWatch stopWatch = new StopWatch();
        Long departmentId = ((Long)(pJoinPoint.getArgs()[0]));
        LOGGER.info("Retrieving Department: " + departmentId);
        stopWatch.start();
        Object result = pJoinPoint.proceed();
        stopWatch.stop();
        LOGGER.info("Department: " + departmentId + " retrieved in : " + stopWatch.getTotalTimeMillis() + "ms");
        return result;
    }

    @Around("execution (* com.example.Task1.controller.DepartmentController.updateDepartment(..))")
    public Object loggUpdateDepartmentMethod(ProceedingJoinPoint pJoinPoint) throws Throwable{

        final StopWatch stopWatch = new StopWatch();
        Department department = ((Department)(pJoinPoint.getArgs()[0]));
        LOGGER.info("Updating Department: " + department.getId());
        stopWatch.start();
        Object result = pJoinPoint.proceed();
        stopWatch.stop();
        LOGGER.info("Department: " + department + " updated in : " + stopWatch.getTotalTimeMillis() + "ms");
        return result;
    }

    @Around("execution (* com.example.Task1.controller.DepartmentController.deleteDepartment(..))")
    public Object loggDeleteDepartmentMethod(ProceedingJoinPoint pJoinPoint) throws Throwable{

        final StopWatch stopWatch = new StopWatch();
        Long departmentId = ((Long)(pJoinPoint.getArgs()[0]));
        LOGGER.info("Deleting Department: " + departmentId);
        stopWatch.start();
        Object result = pJoinPoint.proceed();
        stopWatch.stop();
        LOGGER.info("Department: " + departmentId + " deleted in : " + stopWatch.getTotalTimeMillis() + "ms");
        return result;
    }

    @Around("execution (* com.example.Task1.controller.DepartmentController.deleteAllDepartments(..))")
    public Object loggDeleteDepartmentsMethod(ProceedingJoinPoint pJoinPoint) throws Throwable{

        final StopWatch stopWatch = new StopWatch();
        LOGGER.info("Deleting All Departments ");
        stopWatch.start();
        Object result = pJoinPoint.proceed();
        stopWatch.stop();
        LOGGER.info("All departments deleted in : " + stopWatch.getTotalTimeMillis() + "ms");
        return result;
    }

    //Workers

    @Around("execution (* com.example.Task1.controller.WorkerController.addWorker(..))")
    public Object loggAddWorkerMethod(ProceedingJoinPoint pJoinPoint) throws Throwable{

        final StopWatch stopWatch = new StopWatch();
        Worker worker = ((Worker)(pJoinPoint.getArgs()[0]));
        LOGGER.info("Adding Worker: " +  worker);
        stopWatch.start();
        Object result = pJoinPoint.proceed();
        stopWatch.stop();
        LOGGER.info("Worker: " + worker + " added in : " + stopWatch.getTotalTimeMillis() + "ms");
        return result;
    }

    @Around("execution (* com.example.Task1.controller.WorkerController.retrieveAllWorkers(..))")
    public Object loggRetrieveAllWorkersMethod(ProceedingJoinPoint pJoinPoint) throws Throwable{

        final StopWatch stopWatch = new StopWatch();
        LOGGER.info("Retrieving All Workers");
        Long departmentId = (Long)pJoinPoint.getArgs()[0];
        stopWatch.start();
        Object result = pJoinPoint.proceed();
        stopWatch.stop();
        LOGGER.info("All workers for department "+ departmentId
                + " retrieved in : " + stopWatch.getTotalTimeMillis() + "ms");
        return result;
    }

    @Around("execution (* com.example.Task1.controller.WorkerController.retrieveWorker(..))")
    public Object loggRetrieveWorkerMethod(ProceedingJoinPoint pJoinPoint) throws Throwable{

        final StopWatch stopWatch = new StopWatch();
        Long workerId = ((Long)(pJoinPoint.getArgs()[0]));
        LOGGER.info("Retrieving Worker: " + workerId);
        stopWatch.start();
        Object result = pJoinPoint.proceed();
        stopWatch.stop();
        LOGGER.info("Worker: " + workerId + " retrieved in : " + stopWatch.getTotalTimeMillis() + "ms");
        return result;
    }

    @Around("execution (* com.example.Task1.controller.WorkerController.updateWorker(..))")
    public Object loggUpdateWorkerMethod(ProceedingJoinPoint pJoinPoint) throws Throwable{

        final StopWatch stopWatch = new StopWatch();
        Worker worker = ((Worker)(pJoinPoint.getArgs()[0]));
        LOGGER.info("Updating Worker: " + worker.getId());
        stopWatch.start();
        Object result = pJoinPoint.proceed();
        stopWatch.stop();
        LOGGER.info("Worker: " + worker + " updated in : " + stopWatch.getTotalTimeMillis() + "ms");
        return result;
    }

    @Around("execution (* com.example.Task1.controller.WorkerController.deleteWorker(..))")
    public Object loggDeleteWorkerMethod(ProceedingJoinPoint pJoinPoint) throws Throwable{

        final StopWatch stopWatch = new StopWatch();
        Long workerId = ((Long)(pJoinPoint.getArgs()[0]));
        LOGGER.info("Deleting Worker: " + workerId);
        stopWatch.start();
        Object result = pJoinPoint.proceed();
        stopWatch.stop();
        LOGGER.info("Worker: " + workerId + " deleted in : " + stopWatch.getTotalTimeMillis() + "ms");
        return result;
    }

    @Around("execution (* com.example.Task1.controller.WorkerController.deleteAllWorkers(..))")
    public Object loggDeleteAllWorkersMethod(ProceedingJoinPoint pJoinPoint) throws Throwable{

        final StopWatch stopWatch = new StopWatch();
        LOGGER.info("Deleting All Workers ");
        stopWatch.start();
        Object result = pJoinPoint.proceed();
        stopWatch.stop();
        LOGGER.info("All workers deleted in : " + stopWatch.getTotalTimeMillis() + "ms");
        return result;
    }

}
