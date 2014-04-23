# Sample gradle configuration for pitest

This project serves two purposes:

1. To demonstrate a sample gradle config for pitest
2. To have a reference project to use in performance testing of pitest

## Sample config

**TODO**

## Using for performance testing

### Setting up

For performance testing, it is vital to be able to vary some parameters to determine the behavior of the tested system.
For pitest, some of the most important parameters are code base size, test coverage and mutation number.

To ge a stable and customizable source set, one can use the LargeClassGenerator class. Its usage is as follows:

```
 -c N   : Number of classes to generate
 -m N   : Number of methods to generate per class
 -p VAL : Package name
 -r VAL : Root folder
 -s     : Run in simulated mode, i.e. do not actually write anything to disk
```

One can also use gradle tasks ```generate``` (with ```-PgenArgs="..."``` to pass CLI params) and ```cleanGenerated```

### Results

Mutation analysis of 100 classes with 10 methods in each takes 3 minutes 40 seconds on my laptop:

```
Java(TM) SE Runtime Environment, 1.8.0-b132
Java HotSpot(TM) 64-Bit Server VM, 25.0-b70
Mac OS X, 10.9.2, x86_64
```

The run to run variance is negligible.