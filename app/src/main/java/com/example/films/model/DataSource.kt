package com.example.films.model

import com.example.films.R

object DataSource {

        fun createDataSet(): ArrayList<Movie> {
            val list = ArrayList<Movie>()

            list.add(
                Movie(
                    "Dolittle",
                    2020,
                    56,
                    "Many animals and Robert.",
                    image = R.drawable.dolittle
                )
            )
            list.add(
                Movie(
                    "Solo: A Star Wars story",
                    2018,
                    69,
                    "This film present Han great adventures.",
                    image = R.drawable.solo
                )
            )

            list.add(
                Movie(
                    "Captain Marvel",
                    2019,
                    69,
                    "Every feminist favourite film.",
                    image = R.drawable.captain
                )
            )
            list.add(
                Movie(
                    "How to Train Your Dragon 3",
                    2019,
                    75,
                    "Animated ",
                    image = R.drawable.how
                )
            )
            list.add(
                Movie(
                    "Pirates of Caribbean",
                    2003,
                    80,
                    "Many arrr and swords.",
                    image = R.drawable.pirates

                )
            )
            list.add(
                Movie(
                    "Deadpool",
                    2016,
                    80,
                    "The man's life who can not die.",
                    image = R.drawable.deadpool

                )
            )
            return list
        }
    }
