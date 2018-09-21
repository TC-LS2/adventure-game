import { connect } from "react-redux"
import getGame from "../selectors/getGame"
import GameView from "./GameView"

const StateGameView = connect(state => ({
  game: getGame(state) || { player: {}, room: {} },
}))(GameView)
export default StateGameView
