import { connect } from "react-redux"
import { setUsername } from "../actions/setUsername"
import { login } from "../actions/login"
import getUsername from "../selectors/getUsername"
import LoginForm from "./LoginForm"

const StateLoginForm = connect(
  state => ({
    username: getUsername(state),
  }),
  dispatch => ({
    onUsername: username => dispatch(setUsername(username)),
    onSubmit: username => dispatch(login(username)),
  }),
)(LoginForm)

export default StateLoginForm
