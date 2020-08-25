package algorithms.stringPatternMatching;

/**
 * 暴力匹配算法
 * 最好的情况目标字符串(设长度为n)中前段字符与匹配字符串(设长度为m)相等，
 *      需要比较：1 * 字符串的长度m 次  时间复杂度为 O(m)
 * 最坏情况目标字符串中没有与匹配字符串相等的
 *      需要比较：（目标字符串长度n - 匹配字符串长度m-1） * 字符串的长度m 次  m<<n,时间复杂度为O(n*m)
 *
 * Brute- Force 算法 是一 种 带 回溯 的 模式 匹配 算法， 它将 目标 串 中 所有 长度 为
 *m 的 子串 依次 与 模式 串 匹配， 这样 虽然 没有 丢失 任何 匹配 可能， 但是 每次 匹配 没有 利用 前一 次 匹配 的 比较 结果， 使 算法 中 存在 较多 的 重复 比较， 降低 了 算法 效率。
 *
 */
public class BruteForce {

    /**
     *a a b b b b b c a b c
     *a b c
     * Brute- Force 算法 的 子串 匹配 过程… 是，
     * 将目标串中字符ti（ 0 ≤ i ＜ n）与模式串中字符 pj（ 0 ≤ j ＜ m） 进行 比较：
     *1) 若 ti= p j， 继续 比较 ti+ 1 与 p j+ 1， 直到 j= m- 1，
     * 则″ ti- m+ 1… ti″ 与″ p0… pm- 1″ 匹配 成功， 返回 模式 串在 目标 串 中 匹配 子串 序号 i- m+ 1。
     *
     *2) 若 ti ≠ p j， 表示″ ti- j… ti… ti- j+ m- 1″ 与″ p0… p j… pm- 1″ 匹配 失败；
     * 目标 串 下一个 匹配 子串 是″ ti- j+ 1… ti- j+ m″， 继续 比较 ti- j+ 1 与 p0。
     * 此时， 目标 串 回溯， 从 ti 退回 到 ti- j+ 1。
     *
     */

    public static void main(String[] args) {
        BruteForce str = new BruteForce("aababcd");
        int i = str.indexof("abcd");
        System.out.println("匹配的位置为：" + i);
    }

    char[] targetStr,  patterStr;

    public BruteForce(String targetStr) {
        this.targetStr = targetStr.toCharArray();
    }


    /**
     * 返回该字符串在{@code BruteForce(String targetStr)}中传入的差数中第一个匹配的位置
     * @param patteStr 需要匹配的字符串
     * @return 第一次匹配的位置
     */
    int indexof(String patteStr) {
        this.patterStr = patteStr.toCharArray();

        for(int i=0; i<targetStr.length; i++) {

                       /* System.out.println("*****************");
                        System.out.println("pattern:\t" + i);*/

            for(int j=0; j<patterStr.length; j++){

                       /* System.out.println("targerstr:\t" + (i+j) + "\t" + targetStr[i+j] + "\tmatching\t"
                        + "patternStr:\t" + j + "\t" + patterStr[j]);*/

                if (targetStr[i+j] == patterStr[j]) {
                    if (j == patterStr.length - 1)
                        return i;
                }
                else
                    break;

            }
        }
        return -1;
    }



}
