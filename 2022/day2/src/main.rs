use std::fs;

fn part2(data: &str) -> u32 {
    data.lines()
        .map(|i| {
            let opponent = (i.chars().nth(0).unwrap() as u32) - ('A' as u32);
            let outcome = (i.chars().nth(2).unwrap() as u32) - ('X' as u32);
            outcome * 3 + (outcome + opponent + 2) % 3 + 1
        })
        .sum()
}

fn part1(data: &str) -> u32 {
    data.lines()
        .map(|i| {
            let opponent = (i.chars().nth(0).unwrap() as u32) - ('A' as u32);
            let player = (i.chars().nth(2).unwrap() as u32) - ('X' as u32);
            (player + 4 - opponent) % 3 * 3 + player + 1
        })
        .sum()
}

fn main() {
    let data = fs::read_to_string("day2.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
