import java.util.Queue;
import java.util.LinkedList;
public class IntcodeNetwork extends Intcode {
    public Queue<Long> output;
    public IntcodeNetwork(String input) {
        super(input);
        output = new LinkedList<>();
    }
    public long[] execute(int i) {
        while (i < program.length && i > -1) {
            String init = ""+program[i];
            int opcode = Integer.parseInt(init.length() >= 2 ? init.substring(init.length()-2) : ""+init.charAt(init.length()-1));
            int[] mode = new int[3];
            for (int j = 0; j < mode.length && init.length()-3-j >= 0; j++)
                mode[j] = Integer.parseInt(""+init.charAt(init.length()-3-j));
            int idx3 = getMode(i+3, mode[2]), idx2 = getMode(i+2, mode[1]), idx1 = getMode(i+1, mode[0]);
            if (opcode == 99) { // break
                paused = -1;
                break;
            } else if (opcode == 1) { // add
                program[idx3] = program[idx1] + program[idx2];
                i += 4;
            } else if (opcode == 2) { // multiply
                program[idx3] = program[idx1] * program[idx2]; 
                i += 4;
            } else if (opcode == 3) { // input
                if (numInputs >= inputs.size())
                    inputs.add(-1l);
                program[idx1] = inputs.get(numInputs);
                i += 2; numInputs++;
                if (program[idx1] == -1) {
                    paused = i; return null;
                }
            } else if (opcode == 4) { // output
                output.add(program[idx1]);
                i += 2;
                paused = i; break;
            } else if (opcode == 5) { // jump-if-true
                if (program[idx1] != 0)
                    i = (int)program[idx2];
                else
                    i += 3;
            } else if (opcode == 6) { // jump-if-false
                if (program[idx1] == 0)
                    i = (int)program[idx2];
                else
                    i += 3;
            } else if (opcode == 7) { // less than
                if (program[idx1] < program[idx2])
                    program[idx3] = 1;
                else
                    program[idx3] = 0;
                i += 4;
            } else if (opcode == 8) { // equals
                if (program[idx1] == program[idx2])
                    program[idx3] = 1;
                else
                    program[idx3] = 0;
                i += 4;
            } else if (opcode == 9) { // relative base offset
                relBase += program[idx1];
                i += 2;
            }
        }
        return program;
    }
}
