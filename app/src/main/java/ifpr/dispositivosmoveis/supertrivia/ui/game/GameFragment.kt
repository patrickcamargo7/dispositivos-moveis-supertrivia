package ifpr.dispositivosmoveis.supertrivia.ui.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ifpr.dispositivosmoveis.supertrivia.R
import ifpr.dispositivosmoveis.supertrivia.adapters.AnswerAdapter
import ifpr.dispositivosmoveis.supertrivia.dao.GameDAO
import ifpr.dispositivosmoveis.supertrivia.dao.RequireInitGameFailure
import ifpr.dispositivosmoveis.supertrivia.models.Answer
import ifpr.dispositivosmoveis.supertrivia.models.Problem
import ifpr.dispositivosmoveis.supertrivia.util.GameSession
import ifpr.dispositivosmoveis.supertrivia.util.UserSession
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_game.view.*

class GameFragment : Fragment(), View.OnClickListener {
    private val dao: GameDAO = GameDAO()
    private lateinit var answerAdapter: AnswerAdapter

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

        btnFinishGame.setOnClickListener(this)
        btnSendAnswer.setOnClickListener(this)

        initGame(view)
    }

    private fun initGame(view: View) {
        progressBarGame.visibility = View.VISIBLE
        setVisibleAllItems(View.GONE)
        enableClickOnButtons(false)

        findOrCreateGame(view)
    }

    private fun findOrCreateGame(view: View)
    {
        context?.let {
            val game = GameSession.getGame(it)
            val user = UserSession.getUser(it)

            dao.findOrCreate(game.difficulty, game.category?.id, user.token, { gameAPI ->
                if (gameAPI.data.isExistingGame()) {
                    currentProblem(view)
                } else {
                    nextProblem(view)
                }
            },
                {
                }
            )
        }
    }

    private fun currentProblem(view: View) {
        val user = context?.let {
            val user = UserSession.getUser(it)
            dao.findCurrentProblem(user.token, { problemAPI ->
                setProblem(problemAPI.data, view)
            }, { error ->
                if (error is RequireInitGameFailure) {
                    nextProblem(view)
                }
            })
        }
    }


    private fun nextProblem(view: View) {
        val user = context?.let {
            val user = UserSession.getUser(it)

            dao.findNextProblem(user.token, { problemAPI ->
                setProblem(problemAPI.data, view)
            }, {
                progressBarGame.visibility = View.GONE
            })
        }
    }

    private fun setProblem(problem: Problem, view: View) {
        tvProblem.text = problem.question

        answerAdapter = AnswerAdapter(problem.answers)
        view.listAnswers.adapter = answerAdapter
        view.listAnswers.layoutManager =
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSendAnswer -> {
                sendAnswer()
            }
            R.id.btnFinishGame -> {
            }
            else -> {
            }
        }
    }

    private fun sendAnswer() {
        val user = context?.let {
            val user = UserSession.getUser(it)

            val selectAnswer = answerAdapter.getSelectedItem()

            enableClickOnButtons(false)

            dao.sendAnswer(selectAnswer!!.order, user.token, { fixAnswerAPI ->
                if (fixAnswerAPI.data.isCorrect()) {
                    answerAdapter.setCorrect(selectAnswer)
                } else {
                    answerAdapter.setError(selectAnswer)
                    answerAdapter.setCorrect(fixAnswerAPI.data.correctAnswer)
                }
            }, {})
        }
    }

}