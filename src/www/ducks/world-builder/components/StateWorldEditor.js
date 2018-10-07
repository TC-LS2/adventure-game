import { connect } from "react-redux"
import { setWorld } from "../actions/setWorld"
import { putWorld } from "../actions/putWorld"
import getWorld from "../selectors/getWorld"
import WorldEditor from "./WorldEditor"

const StateWorldEditor = connect(
  state => ({ world: getWorld(state) }),
  dispatch => ({
    onChange: world => dispatch(setWorld(world)),
    onSave: world => dispatch(putWorld(world)),
  }),
)(WorldEditor)

export default StateWorldEditor
