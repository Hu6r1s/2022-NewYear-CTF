export function UpdateChallenge(ChallengeData) {
    return {
        type: "CHALLENGE_UPDATE",
        payload: ChallengeData
    };
}
export function SolveChallenge(ChallengeData) {
    return {
        type: "SOLVE_CHALLENGE",
        payload: ChallengeData
    };
}
export function SolvedList(SolvedData) {
    return {
        type: "SOLVED_LIST",
        payload: SolvedData
    };
}
