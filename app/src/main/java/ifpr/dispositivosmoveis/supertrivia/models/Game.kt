package ifpr.dispositivosmoveis.supertrivia.models

data class Game(
    var difficulty: String?,
    var category: Category?,
    var asserts: Number = 0,
    var errors: Number = 0,
    var score: Number = 0
);