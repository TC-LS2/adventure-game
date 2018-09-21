import { connect } from "react-redux"
import Button from "../../../common/components/Button"
import { postCommand } from "../actions/postCommand"

const PostCommandButton = connect(
  undefined,
  (dispatch, { username, command, arguments: args = [] }) => ({
    onClick: () => dispatch(postCommand(username, command, args)),
  }),
)(Button)

export default PostCommandButton
