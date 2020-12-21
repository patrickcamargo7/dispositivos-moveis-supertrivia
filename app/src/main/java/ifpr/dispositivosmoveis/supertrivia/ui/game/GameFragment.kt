package ifpr.dispositivosmoveis.supertrivia.ui.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ifpr.dispositivosmoveis.supertrivia.R
import ifpr.dispositivosmoveis.supertrivia.adapters.AnswerAdapter
import ifpr.dispositivosmoveis.supertrivia.dao.GameDAO
import ifpr.dispositivosmoveis.supertrivia.dao.RequireInitGameFailure
import ifpr.dispositivosmoveis.supertrivia.models.Problem
import ifpr.dispositivosmoveis.supertrivia.util.GameSession
import ifpr.dispositivosmoveis.supertrivia.util.Helpers.Companion.showErrorConnection
import ifpr.dispositivosmoveis.supertrivia.util.UserSession
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_game.view.*
import java.io.IOException

class GameFragment : Fragment(), View.OnClickListener {
    private val dao: GameDAO = GameDAO()
    private lateinit var answerAdapter: AnswerAdapter
    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        btnFinishGame.setOnClickListener(this)
        btnSendAnswer.setOnClickListener(this)
        btnNextProblem.setOnClickListener(this)

        makeProblem()
    }

    private fun makeProblem() {
        progressBarGame.visibility = View.VISIBLE
        setVisibleAllItems(View.GONE)
        enableClickOnButtons(false)
        btnNextProblem.visibility = View.GONE
        progressBarAnswer.visibility = View.GONE

        findOrCreateGame()
    }

    private fun findOrCreateGame()
    {
        context?.let {
            val game = GameSession.getGame(it)
            val user = UserSession.getUser(it)

            dao.findOrCreate(game.difficulty, game.category?.id, user.token, { gameAPI ->
                if (gameAPI.data.isExistingGame()) {
                    currentProblem()
                } else {
                    nextProblem()
                }
            },
                { error ->
                    activity?.let { it1 -> showErrorConnection(error, it1) }
                }
            )
        }
    }

    private fun currentProblem() {
        val user = context?.let {
            val user = UserSession.getUser(it)
            dao.findCurrentProblem(user.token, { problemAPI ->
                setProblem(problemAPI.data)
            }, { error ->
                if (error is RequireInitGameFailure) {
                    nextProblem()
                }
            })
        }
    }


    private fun nextProblem() {
        val user = context?.let {
            val user = UserSession.getUser(it)

            dao.findNextProblem(user.token, { problemAPI ->
                setProblem(problemAPI.data)
            }, {
                progressBarGame.visibility = View.GONE
            })
        }
    }

    private fun setProblem(problem: Problem) {
        tvProblem.text = problem.question

        answerAdapter = AnswerAdapter(problem.answers)
        listAnswers.adapter = answerAdapter
        listAnswers.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        setVisibleAllItems(View.VISIBLE)
        progressBarGame.visibility = View.GONE
        enableClickOnButtons(true)
    }

    private fun setVisibleAllItems(visible: Int) {
        btnSendAnswer.visibility = visible
        btnFinishGame.visibility = visible
        tvProblem.visibility = visible
        listAnswers.visibility = visible
    }

    private fun enableClickOnButtons(enable: Boolean)
    {
        btnSendAnswer.isEnabled = enable
        btnFinishGame.isEnabled = enable
    }

    private fun setVisibleButtons(visible: Int) {
        btnSendAnswer.visibility = visible
        btnFinishGame.visibility = visible
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSendAnswer -> {
                sendAnswer()
            }
            R.id.btnFinishGame -> {
                finishGame()
            }
            R.id.btnNextProblem -> {
                makeProblem()
            }
            else -> {
            }
        }
    }

    private fun finishGame() {
        val user = context?.let {
            val user = UserSession.getUser(it)

            dao.finishGame( user.token, { gameAPI ->
                navController!!.navigate(R.id.action_gameFragment_to_finishGameFragment)
            }, {
            })
        }
    }

    private fun sendAnswer() {
        val user = context?.let {
            val user = UserSession.getUser(it)

            val selectAnswer = answerAdapter.getSelectedItem() ?: return

            enableClickOnButtons(false)
            setVisibleButtons(View.GONE)

            progressBarAnswer.visibility = View.VISIBLE

            dao.sendAnswer(selectAnswer!!.order, user.token, { fixAnswerAPI ->
                if (fixAnswerAPI.data.isCorrect()) {
                    answerAdapter.setCorrect(selectAnswer)
                    GameSession.addAssert(it)
                } else {
                    answerAdapter.setError(selectAnswer)
                    answerAdapter.setCorrect(fixAnswerAPI.data.correctAnswer)
                    GameSession.addError(it)
                }
                GameSession.setScore(it, fixAnswerAPI.data.score)

                btnNextProblem.visibility = View.VISIBLE
                progressBarAnswer.visibility = View.GONE
            }, {
                progressBarAnswer.visibility = View.GONE
            })
        }
    }

}