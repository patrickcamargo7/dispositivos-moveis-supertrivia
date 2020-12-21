package ifpr.dispositivosmoveis.supertrivia.util

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import ifpr.dispositivosmoveis.supertrivia.models.Game

class GameSession {
    companion object {
        private const val GAME_KEY = "_game";
        private const val PREFS_GAME_FILE_KEY = "_prefs_file_game_key";

        fun mergeGameSettings(context: Context, game: Game) {
            val currentGameSettings = getGame(context)

            game.category = currentGameSettings.category
            game.difficulty = currentGameSettings.difficulty

            setGameSettings(context, game)
        }

        fun setGameSettings(context: Context, game: Game) {
            val sharedPref = context.getSharedPreferences(PREFS_GAME_FILE_KEY, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                var gameString = Gson().toJson(game)
                putString(GAME_KEY, gameString)
                commit()
            }
        }

        fun isConfigured(context: Context): Boolean {
            return try {
                getGame(context)
                true
            } catch (e: Exception) {
                false
            }
        }

        fun getGame(context: Context): Game {
            val sharedPref = context.getSharedPreferences(PREFS_GAME_FILE_KEY, Context.MODE_PRIVATE)
            val game = sharedPref.getString(GAME_KEY, "")

            if (game!!.isEmpty())
                throw Exception();
            return Gson().fromJson(game, Game::class.java)
        }

        fun reset(context: Context)
        {
            val sharedPref = context?.getSharedPreferences(PREFS_GAME_FILE_KEY, Context.MODE_PRIVATE)
            sharedPref.edit().remove(GAME_KEY).commit()
        }
    }
}