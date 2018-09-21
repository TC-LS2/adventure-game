import { push } from "connected-react-router"
import { connect } from "react-redux"
import Button from "../../common/components/Button"
import { setWorld } from "../../ducks/world-builder/actions/setWorld"

const GoToBuilderButton = connect(
  () => ({ children: "[ Build the World ]" }),
  (dispatch, { world }) => ({
    onClick: () => {
      dispatch(setWorld(world))
      dispatch(push("/builder"))
    },
  }),
)(Button)

export default GoToBuilderButton
