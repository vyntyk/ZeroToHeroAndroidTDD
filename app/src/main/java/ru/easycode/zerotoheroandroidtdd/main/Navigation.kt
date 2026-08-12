package ru.easycode.zerotoheroandroidtdd.main

interface Navigation {

    interface Update {
        fun update(screen: Screen)
    }

    interface Mutable : Update

    interface Provide {
        fun navigation(): Navigation.Mutable
    }
}

interface Screen

object FoldersListScreen : Screen
