package com.carlgo11.simpleautomessage.metrics;

    public class SimplePlotter extends Metrics.Plotter {

        public SimplePlotter(final String name)
        {
            super(name);
        }

        @Override
        public int getValue()
        {
            return 1;
        }
    }