import { connect } from "react-redux"
import Button from "./Button"
import { push } from "connected-react-router"

const LinkButton = connect(
  undefined,
  (dispatch, { to }) => ({ onClick: () => dispatch(push(to)) }),
)(Button)

export default LinkButton
