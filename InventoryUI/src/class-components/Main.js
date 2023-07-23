import { Component } from "react";

class Main extends Component {
    constructor() {
        super();
        this.state = {
            arry: [4, 7, 2, 5, 9, 1, 6],
            count: 0
        }
    }

    /* A class must have a render function */
    render() {
        return (
            <div style={{ margin: '10px' }}>
                <h1>Welcome to React class component!!!</h1>
                {
                    this.state.arry.map((e, index) => {
                        return (
                            <li key={index}>{e}</li>
                        )
                    })
                }

                <hr />
                <button onClick={() => { this.sortArry('ASC') }}>Sort - ASC</button>
                <button onClick={() => { this.sortArry('DESC') }}>Sort - DESC</button>
            </div>
        );
    }

    sortArry(direction) {
        let arr = this.state.arry;
        switch (direction) {
            case 'ASC':
                arr.sort((a, b) => a - b);
                break;
            case 'DESC':
                arr.sort((a, b) => b - a);
                break;
            default:
                break;
        }
        this.setState({
            arry: arr
        })
    }
}
export default Main; 