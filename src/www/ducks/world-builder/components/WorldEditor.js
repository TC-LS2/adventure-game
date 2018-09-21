import React, { Component } from "react"
import Button from "../../../common/components/Button"
import Editor from "../../../common/components/Editor"

class WorldEditor extends Component {
  onRooms = rooms => this.props.onChange({ rooms })
  onMonsters = monsters => this.props.onChange({ monsters })
  onItems = items => this.props.onChange({ items })
  onSave = () => this.props.onSave(this.props.world)

  render() {
    const { rooms, monsters, items } = this.props.world

    return (
      <div>
        <h3>Rooms</h3>
        <Editor value={rooms} onChange={this.onRooms} />
        <h3>Monsters</h3>
        <Editor value={monsters} onChange={this.onMonsters} />
        <h3>Items</h3>
        <Editor value={items} onChange={this.onItems} />
        <br />
        <br />
        <br />
        <Button onClick={this.onSave}>[ Build World ]</Button>
      </div>
    )
  }
}

export default WorldEditor
