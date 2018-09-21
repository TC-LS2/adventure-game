import { connect } from "react-redux"
import hasGame from "../../game/selectors/hasGame"

const IfLoginRequired = connect((state, { children }) => ({
  children: !hasGame(state) && children,
}))(({ children }) => children)

export default IfLoginRequired
