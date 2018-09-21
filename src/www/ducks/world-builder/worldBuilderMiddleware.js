import rest from "../../common/rest.js"
import { setAlert } from "../alert/actions/setAlert"
import { PUT_WORLD } from "./actions/putWorld"

const builderWorldMiddleware = ({ dispatch }) => next => async action => {
  next(action)

  switch (action.type) {
    case PUT_WORLD: {
      const { world } = action

      try {
        await rest.put("/api/v1/world", world)
        dispatch(setAlert("World created successfully."))
      } catch (e) {
        dispatch(setAlert("World creation FAILED."))
      }
      break
    }
    default: // nothing
  }
}

export default builderWorldMiddleware
