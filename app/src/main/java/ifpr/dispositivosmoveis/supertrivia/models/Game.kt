package ifpr.dispositivosmoveis.supertrivia.models

data class Game(
    var difficulty: String?,
    var category: Category?,
    var asserts: Number = 0,
    var errors: Number = 0,
    var score: Number = 0,
    var creation: String? = null,
    var status: String? = null,
    var startedAt: String? = null
) {
    fun isPlaying() : Boolean {
        return status != null && status == "playing"
    }

    fun isStarted() : Boolean {
        return status != null || isExistingGame()
    }

    fun isExistingGame() : Boolean {
        return creation != null && creation == "existing_game"
    }
}