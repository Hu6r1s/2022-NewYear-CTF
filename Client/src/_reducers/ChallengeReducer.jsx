export default function (
    state = {
        challenge: [],
        solved: []
    },
    action
) {
    switch (action.type) {
        case "CHALLENGE_UPDATE":
            return { ...state, challenge: action.payload };
        case "SOLVED_LIST": // 로그인 했을 때
            return { ...state, solved: action.payload, ...state.solved };
        case "SOLVE_CHALLENGE": // 문제 풀었을 때
            return { ...state, solved: [action.payload, ...state.solved] };
        default:
            return state;
    }
}
