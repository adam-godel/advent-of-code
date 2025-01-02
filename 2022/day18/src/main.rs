use std::fs;

fn recur(
    cache: &mut Vec<(i32, i32, i32)>,
    cubes: &Vec<(i32, i32, i32)>,
    cur: (i32, i32, i32),
    max: (i32, i32, i32),
) -> u32 {
    if cur.0 < -1
        || cur.0 > max.0
        || cur.1 < -1
        || cur.1 > max.1
        || cur.2 < -1
        || cur.2 > max.2
        || cubes.contains(&cur)
        || cache.contains(&cur)
    {
        return 0;
    }
    cache.push(cur);
    ([
        cubes.contains(&(cur.0 - 1, cur.1, cur.2)),
        cubes.contains(&(cur.0 + 1, cur.1, cur.2)),
        cubes.contains(&(cur.0, cur.1 - 1, cur.2)),
        cubes.contains(&(cur.0, cur.1 + 1, cur.2)),
        cubes.contains(&(cur.0, cur.1, cur.2 - 1)),
        cubes.contains(&(cur.0, cur.1, cur.2 + 1)),
    ]
    .into_iter()
    .filter(|j| *j)
    .count() as u32)
        + recur(cache, &cubes, (cur.0 - 1, cur.1, cur.2), max)
        + recur(cache, &cubes, (cur.0 + 1, cur.1, cur.2), max)
        + recur(cache, &cubes, (cur.0, cur.1 - 1, cur.2), max)
        + recur(cache, &cubes, (cur.0, cur.1 + 1, cur.2), max)
        + recur(cache, &cubes, (cur.0, cur.1, cur.2 - 1), max)
        + recur(cache, &cubes, (cur.0, cur.1, cur.2 + 1), max)
}

fn part2(data: &str) -> u32 {
    let cubes: Vec<(i32, i32, i32)> = data
        .lines()
        .map(|i| {
            let mut iter = i.split(",").map(|i| i.parse().unwrap());
            (
                iter.next().unwrap(),
                iter.next().unwrap(),
                iter.next().unwrap(),
            )
        })
        .collect();
    let max = *[
        cubes.iter().map(|i| i.0).max().unwrap(),
        cubes.iter().map(|i| i.1).max().unwrap(),
        cubes.iter().map(|i| i.2).max().unwrap(),
    ]
    .iter()
    .max()
    .unwrap();
    let mut cache = Vec::new();
    recur(
        &mut cache,
        &cubes,
        (-1, -1, -1),
        (max + 1, max + 1, max + 1),
    )
}

fn part1(data: &str) -> u32 {
    let cubes: Vec<(i32, i32, i32)> = data
        .lines()
        .map(|i| {
            let mut iter = i.split(",").map(|i| i.parse().unwrap());
            (
                iter.next().unwrap(),
                iter.next().unwrap(),
                iter.next().unwrap(),
            )
        })
        .collect();
    let mut result = 0;
    for i in &cubes[..] {
        result += 6 - [
            cubes.contains(&(i.0 - 1, i.1, i.2)),
            cubes.contains(&(i.0 + 1, i.1, i.2)),
            cubes.contains(&(i.0, i.1 - 1, i.2)),
            cubes.contains(&(i.0, i.1 + 1, i.2)),
            cubes.contains(&(i.0, i.1, i.2 - 1)),
            cubes.contains(&(i.0, i.1, i.2 + 1)),
        ]
        .into_iter()
        .filter(|j| *j)
        .count() as u32;
    }
    result
}

fn main() {
    let data = fs::read_to_string("day18.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
