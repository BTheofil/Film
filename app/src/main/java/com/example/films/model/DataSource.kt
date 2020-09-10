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
                    image = R.drawable.dolittle,
                    categoryArrayList = arrayListOf(Category.HUMOR, Category.COMEDY)
                )
            )
            list.add(
                Movie(
                    "Solo: A Star Wars story",
                    2018,
                    70,
                    "This film present Han great adventures.",
                    image = R.drawable.solo,
                    categoryArrayList = arrayListOf(Category.FAMILY, Category.FAMILY)
                )
            )

            list.add(
                Movie(
                    "Captain Marvel",
                    2019,
                    69,
                    "Every feminist favourite film.",
                    image = R.drawable.captain,
                    categoryArrayList = arrayListOf(Category.FEMINIST, Category.COMEDY)

                )
            )
            list.add(
                Movie(
                    "How to Train Your Dragon 3",
                    2017,
                    75,
                    "Animated ",
                    image = R.drawable.how,
                    categoryArrayList = arrayListOf(Category.ANIMATED, Category.SCI_FI)
                )
            )
            list.add(
                Movie(
                    "Pirates of Caribbean",
                    2003,
                    81,
                    "Many arrr and swords.",
                    image = R.drawable.pirates,
                    categoryArrayList = arrayListOf(Category.COMEDY, Category.FAMILY)
                )
            )
            list.add(
                Movie(
                    "Deadpool",
                    2016,
                    80,
                    "The man's life who can not die.",
                    image = R.drawable.deadpool,
                    categoryArrayList = arrayListOf(Category.HUMOR, Category.FAMILY)
                )
            )
            return list
        }
    }
