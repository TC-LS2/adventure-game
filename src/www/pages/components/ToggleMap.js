import React from "react"

import Map from "../../common/components/Map"
import Toggle from "../../common/components/Toggle"

const ToggleMap = ({ map }) => (
  <div>
    <Toggle titleOff="v  Show map" titleOn="^  Hide map" firstVisible={true}>
      <Map map={map} style={{ marginTop: 0 }} />
    </Toggle>
  </div>
)

export default ToggleMap
