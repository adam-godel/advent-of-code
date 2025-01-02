use std::fs;

fn part2() -> String {
    String::from("Start The Blender!")
}

fn part1(data: &str) -> String {
    let mut val: i64 = data
        .lines()
        .map(|i| {
            i.chars()
                .map(|j| match j {
                    '2' => 2,
                    '1' => 1,
                    '-' => -1,
                    '=' => -2,
                    _ => 0,
                })
                .fold(0, |res, val| res * 5 + val)
        })
        .sum();
    let mut result = String::new();
    while val > 0 {
        result.insert(
            0,
            match val % 5 {
                0 => '0',
                1 => '1',
                2 => '2',
                3 => '=',
                4 => '-',
                _ => '\0',
            },
        );
        val = ((val as f64) / 5.0).round() as i64;
    }
    result
}

fn main() {
    let data = fs::read_to_string("day25.txt").unwrap();
    println!("{}\n{}", part1(&data), part2());
}
